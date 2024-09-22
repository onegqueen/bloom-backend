package com.example.bloombackend.restdocs;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.bloombackend.bottlemsg.controller.dto.request.CreateBottleMessageReactionRequest;
import com.example.bloombackend.bottlemsg.controller.dto.request.CreateBottleMessageRequest;
import com.example.bloombackend.bottlemsg.entity.BottleMessageEntity;
import com.example.bloombackend.bottlemsg.entity.BottleMessageReaction;
import com.example.bloombackend.bottlemsg.entity.BottleMessageReceiptLog;
import com.example.bloombackend.bottlemsg.entity.Nagativity;
import com.example.bloombackend.bottlemsg.entity.ReactionType;
import com.example.bloombackend.bottlemsg.repository.BottleMessageLogRepository;
import com.example.bloombackend.bottlemsg.repository.BottleMessageRepository;
import com.example.bloombackend.global.config.JwtTokenProvider;
import com.example.bloombackend.oauth.OAuthProvider;
import com.example.bloombackend.user.entity.UserEntity;
import com.example.bloombackend.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
public class BottleMessageRestDocsTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BottleMessageRepository bottleMessageRepository;

	@Autowired
	private BottleMessageLogRepository bottleMessageLogRepository;

	@Autowired
	private UserRepository userRepository;

	@SpyBean
	private JwtTokenProvider jwtTokenProvider;

	private UserEntity testUser;

	private UserEntity testSender;

	private String mockToken;

	private ObjectMapper objectMapper;

	private BottleMessageEntity bottleMessage1;

	private BottleMessageEntity bottleMessage2;
	@Autowired
	private ParameterNamesModule parameterNamesModule;

	@BeforeEach
	void setUp() {
		objectMapper = new ObjectMapper();
		mockToken = "jwtToken";
		testUser = userRepository.save(new UserEntity(OAuthProvider.KAKAO, "testUser", "testId"));
		doReturn(testUser.getId()).when(jwtTokenProvider).getUserIdFromToken(mockToken);
		testSender = userRepository.save(new UserEntity(OAuthProvider.KAKAO, "testSender", "testSenderId"));
		doReturn(testUser.getId()).when(jwtTokenProvider).getUserIdFromToken(mockToken);
		bottleMessage1 = bottleMessageRepository.save(
			BottleMessageEntity.builder()
				.user(testSender)
				.title("내일은 또 다른날")
				.content("오늘은 금요일 내일은 토요일")
				.postcardUrl(
					"https://bloom-bucket-8430.s3.ap-northeast-2.amazonaws.com/postCard/KakaoTalk_20240919_002957024_01.jpg")
				.nagativity(Nagativity.LOWER)
				.build()
		);
		bottleMessage2 = bottleMessageRepository.save(
			BottleMessageEntity.builder()
				.user(testSender)
				.title("모두모두 화이팅")
				.content("모두 모두 화이팅")
				.postcardUrl(
					"https://bloom-bucket-8430.s3.ap-northeast-2.amazonaws.com/postCard/KakaoTalk_20240919_002957024_01.jpg")
				.nagativity(Nagativity.LOWER)
				.build()
		);
	}

	@Test
	@DisplayName("API - 유리병 메시지 등록")
	void createBottleMessageTest() throws Exception {
		//given
		CreateBottleMessageRequest request = new CreateBottleMessageRequest("힘든하루였지만", "보람차",
			"https://bloom-bucket-8430.s3.ap-northeast-2.amazonaws.com/postCard/KakaoTalk_20240919_002957024_01.jpg");

		//when & then
		mockMvc.perform(post("/api/bottle-messages")
				.header("Authorization", mockToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andDo(document("api-bottle-message-test/post-bottle-message",
				responseFields(
					fieldWithPath("id").description("등록한 유리병 메시지 아이디"),
					fieldWithPath("analysis").description("등록한 유리병 메시지의 분석 결과"),
					fieldWithPath("analysis.emotions[]").description("분석 결과의 감정 정보 리스트"),
					fieldWithPath("analysis.emotions[].emotion").description("분석된 감정"),
					fieldWithPath("analysis.emotions[].percentage").description("해당감정의 퍼센트"),
					fieldWithPath("analysis.negativeImpact").description("위험성 (UPPER,MIDDLE,LOWER)")
				)
			));
	}

	@Test
	@DisplayName("API - 유저 유리병 메시지 목록 조회")
	void getUserBottleMessages() throws Exception {
		//given
		BottleMessageReceiptLog log1 = BottleMessageReceiptLog.builder()
			.recipient(testUser)
			.message(bottleMessage1)
			.build();
		BottleMessageReceiptLog log2 = BottleMessageReceiptLog.builder()
			.recipient(testUser)
			.message(bottleMessage2)
			.build();
		bottleMessageLogRepository.saveAll(List.of(log1, log2));

		//when & then
		mockMvc.perform(get("/api/bottle-messages")
				.header("Authorization", mockToken)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("api-bottle-message-test/get-bottle-messages",
				responseFields(
					fieldWithPath("bottleMessageResponses[]").description("유리병 메시지 목록"),
					fieldWithPath("bottleMessageResponses[].log").description("유리병 메시지 수발신 로그 정보"),
					fieldWithPath("bottleMessageResponses[].log.receivedAt").description("수신일시"),
					fieldWithPath("bottleMessageResponses[].log.sentAt").description("발신일시"),
					fieldWithPath("bottleMessageResponses[].messageId").description("유리병 메시지 아이디"),
					fieldWithPath("bottleMessageResponses[].title").description("유리병 메시지 제목"),
					fieldWithPath("bottleMessageResponses[].postCardUrl").description("유리병 메시지 편지지 url"),
					fieldWithPath("bottleMessageResponses[].negativity").description("유리병 메시지 위험도")
				)
			));
	}

	@Test
	@DisplayName("API - 유리병 메시지 상세 조회 조회")
	void getBottleMessage() throws Exception {
		//when & then
		mockMvc.perform(get("/api/bottle-messages/{messageId}", bottleMessage1.getId())
				.header("Authorization", mockToken)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("api-bottle-message-test/get-bottle-message",
				pathParameters(
					parameterWithName("messageId").description("조회할 메시지 아이디")
				),
				responseFields(
					fieldWithPath("message").description("유리병 메시지 정보"),
					fieldWithPath("message.messageId").description("유리병 메시지 아이디"),
					fieldWithPath("message.title").description("유리병 메시지 제목"),
					fieldWithPath("message.content").description("유리병 메시지 내용"),
					fieldWithPath("message.postCardUrl").description("유리병 메시지 편지지 Url"),
					fieldWithPath("message.negativity").description("유리병 메시지 위험도"),
					fieldWithPath("reaction").description("유리병 메시지 반응 정보"),
					fieldWithPath("reaction.isReacted").description("해당 유리병 메시지 유저 반응 여부"),
					fieldWithPath("reaction.like").description("유리병 메시지 좋아요 개수"),
					fieldWithPath("reaction.empathy").description("유리병 메시지 공감해요 개수"),
					fieldWithPath("reaction.cheer").description("유리병 메시지 응원해요 개수")
				)
			));
	}

	@Test
	@DisplayName("API - 유리병 메시지 랜덤 조회")
	void getRandomBottleMessage() throws Exception {
		//when & then
		mockMvc.perform(get("/api/bottle-messages/random")
				.header("Authorization", mockToken)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("api-bottle-message-test/get-random-bottle-message",
				responseFields(
					fieldWithPath("message").description("유리병 메시지 정보"),
					fieldWithPath("message.messageId").description("유리병 메시지 아이디"),
					fieldWithPath("message.title").description("유리병 메시지 제목"),
					fieldWithPath("message.content").description("유리병 메시지 내용"),
					fieldWithPath("message.postCardUrl").description("유리병 메시지 편지지 Url"),
					fieldWithPath("message.negativity").description("유리병 메시지 위험도"),
					fieldWithPath("reaction").description("유리병 메시지 반응 정보"),
					fieldWithPath("reaction.isReacted").description("해당 유리병 메시지 유저 반응 여부"),
					fieldWithPath("reaction.like").description("유리병 메시지 좋아요 개수"),
					fieldWithPath("reaction.empathy").description("유리병 메시지 공감해요 개수"),
					fieldWithPath("reaction.cheer").description("유리병 메시지 응원해요 개수")
				)
			));
	}

	@Test
	@DisplayName("API - 유리병 메시지 반응 등록")
	void postBottleMessageReaction() throws Exception {
		//given
		CreateBottleMessageReactionRequest request = new CreateBottleMessageReactionRequest("LIKE");

		//when & then
		mockMvc.perform(post("/api/bottle-messages/{messageId}/react", bottleMessage1.getId())
				.header("Authorization", mockToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andDo(document("api-bottle-message-test/post-bottle-message-reaction",
				pathParameters(
					parameterWithName("messageId").description("조회할 메시지 아이디")
				),
				responseFields(
					fieldWithPath("isReacted").description("유저의 반응여부"),
					fieldWithPath("like").description("좋아요 개수"),
					fieldWithPath("empathy").description("공감해요 개수"),
					fieldWithPath("cheer").description("응원해요 개수")
				)
			));
	}

	@Test
	@DisplayName("API - 받은 유리병 메시지 삭제")
	void deleteBottleMessage() throws Exception {
		//given
		BottleMessageReceiptLog log1 = BottleMessageReceiptLog.builder()
			.recipient(testUser)
			.message(bottleMessage1)
			.build();
		BottleMessageReceiptLog log2 = BottleMessageReceiptLog.builder()
			.recipient(testUser)
			.message(bottleMessage2)
			.build();
		bottleMessageLogRepository.saveAll(List.of(log1, log2));

		//when & then
		mockMvc.perform(post("/api/bottle-messages/{messageId}/delete", bottleMessage1.getId())
				.header("Authorization", mockToken)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("api-bottle-message-test/delete-bottle-message",
				pathParameters(
					parameterWithName("messageId").description("삭제할 메시지 아이디")
				),
				responseFields(
					fieldWithPath("bottleMessageResponses[]").description("유리병 메시지 목록"),
					fieldWithPath("bottleMessageResponses[].log").description("유리병 메시지 수발신 로그 정보"),
					fieldWithPath("bottleMessageResponses[].log.receivedAt").description("수신일시"),
					fieldWithPath("bottleMessageResponses[].log.sentAt").description("발신일시"),
					fieldWithPath("bottleMessageResponses[].messageId").description("유리병 메시지 아이디"),
					fieldWithPath("bottleMessageResponses[].title").description("유리병 메시지 제목"),
					fieldWithPath("bottleMessageResponses[].postCardUrl").description("유리병 메시지 편지지 url"),
					fieldWithPath("bottleMessageResponses[].negativity").description("유리병 메시지 위험도")
				)
			));
	}

	@Test
	@DisplayName("API - 보낸 유리병 메시지 목록 조회")
	void getSentBottleMessage() throws Exception {
		//given
		bottleMessage1 = BottleMessageEntity.builder()
			.user(testUser)
			.title("내일은 또 다른날")
			.content("오늘은 금요일 내일은 토요일")
			.postcardUrl(
				"https://bloom-bucket-8430.s3.ap-northeast-2.amazonaws.com/postCard/KakaoTalk_20240919_002957024_01.jpg")
			.nagativity(Nagativity.LOWER)
			.build();
		bottleMessage2 = BottleMessageEntity.builder()
			.user(testUser)
			.title("다 죽어버렸으면")
			.content("힘들다")
			.postcardUrl(
				"https://bloom-bucket-8430.s3.ap-northeast-2.amazonaws.com/postCard/KakaoTalk_20240919_002957024_01.jpg")
			.nagativity(Nagativity.UPPER)
			.build();
		bottleMessageRepository.saveAll(List.of(bottleMessage1, bottleMessage2));

		//when & then
		mockMvc.perform(get("/api/bottle-messages/sent")
				.header("Authorization", mockToken)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("api-bottle-message-test/get-sent-bottle-messages",
				pathParameters(
					parameterWithName("messageId").description("삭제할 메시지 아이디")
				),
				responseFields(
					fieldWithPath("bottleMessageResponses[]").description("유리병 메시지 목록"),
					fieldWithPath("bottleMessageResponses[].log").description("유리병 메시지 수발신 로그 정보"),
					fieldWithPath("bottleMessageResponses[].log.receivedAt").description("수신일시"),
					fieldWithPath("bottleMessageResponses[].log.sentAt").description("발신일시"),
					fieldWithPath("bottleMessageResponses[].messageId").description("유리병 메시지 아이디"),
					fieldWithPath("bottleMessageResponses[].title").description("유리병 메시지 제목"),
					fieldWithPath("bottleMessageResponses[].postCardUrl").description("유리병 메시지 편지지 url"),
					fieldWithPath("bottleMessageResponses[].negativity").description("유리병 메시지 위험도")
				)
			));
	}

	@Test
	@DisplayName("API - 유리병 메시지 반응 삭제")
	void deleteBottleMessageReaction() throws Exception {
		//given
		BottleMessageReaction reaction = BottleMessageReaction.builder()
			.reactionType(ReactionType.LIKE)
			.message(bottleMessage1)
			.reactor(testUser)
			.build();

		CreateBottleMessageReactionRequest request = new CreateBottleMessageReactionRequest("LIKE");

		//when & then
		mockMvc.perform(delete("/api/bottle-messages/{messageId}/react", bottleMessage1.getId())
				.header("Authorization", mockToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andDo(document("api-bottle-message-test/delete-bottle-messages-reaction",
				pathParameters(
					parameterWithName("messageId").description("반응을 삭제할 메시지 아이디")
				)
			));
	}
}
