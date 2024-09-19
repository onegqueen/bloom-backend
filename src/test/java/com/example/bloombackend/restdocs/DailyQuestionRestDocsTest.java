package com.example.bloombackend.restdocs;

import com.example.bloombackend.dailyquestion.controller.request.DailyQuestionAnswerRequest;
import com.example.bloombackend.dailyquestion.entity.DailyQuestionEntity;
import com.example.bloombackend.dailyquestion.entity.DailyQuestionLogEntity;
import com.example.bloombackend.dailyquestion.repository.DailyQuestionLogRepository;
import com.example.bloombackend.dailyquestion.repository.DailyQuestionRepository;
import com.example.bloombackend.global.config.JwtTokenProvider;
import com.example.bloombackend.oauth.OAuthProvider;
import com.example.bloombackend.user.entity.UserEntity;
import com.example.bloombackend.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.lang.reflect.Field;

import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
public class DailyQuestionRestDocsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DailyQuestionRepository dailyQuestionRepository;

    @Autowired
    private DailyQuestionLogRepository dailyQuestionLogRepository;

    @Autowired
    private UserRepository userRepository;

    @SpyBean
    private JwtTokenProvider jwtTokenProvider;

    private UserEntity testUser;

    private String mockToken;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        objectMapper = new ObjectMapper();

        // 사용자 생성 및 토큰 처리 모킹
        mockToken = "jwtToken";
        testUser = userRepository.save(new UserEntity(OAuthProvider.KAKAO, "testUser", "testId"));
        doReturn(testUser.getId()).when(jwtTokenProvider).getUserIdFromToken(mockToken);

        // 오늘의 질문 1,2 저장
        DailyQuestionEntity testQuestion1 = dailyQuestionRepository.save(new DailyQuestionEntity("오늘의 질문1"));
        DailyQuestionEntity testQuestion2 = dailyQuestionRepository.save(new DailyQuestionEntity("오늘의 질문2"));

        // 사용자에게 오늘의 질문 1 저장
        dailyQuestionLogRepository.save(new DailyQuestionLogEntity(testUser, testQuestion1));

        // 사용자에게 오늘의 질문 2의 조회 날짜를 리플렉션으로 변경
        DailyQuestionLogEntity testLog = new DailyQuestionLogEntity(testUser, testQuestion2);
        Field createdAtField = DailyQuestionLogEntity.class.getDeclaredField("createdAt");
        createdAtField.setAccessible(true);
        createdAtField.set(testLog, LocalDateTime.of(2024, 9, 1, 12, 0));

        // 사용자에게 오늘의 질문 2에 답변 저장
        testLog.updateAnswer("테스트 답변");
        dailyQuestionLogRepository.save(testLog);
    }

    @Test
    @DisplayName("API - 오늘의 질문 조회")
    void getDailyQuestionTest() throws Exception {
        //when & then
        mockMvc.perform(get("/api/daily-question")
                .header("Authorization", mockToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("daily-question/get-daily-question",
                        responseFields(
                                fieldWithPath("question").description("오늘의 질문")
                        )
                ));
    }

    @Test
    @DisplayName("API - 특정 날짜의 오늘의 질문 답변 조회")
    void getDailyQuestionAnswerTest() throws Exception {
        //when & then
        mockMvc.perform(get("/api/daily-question/answer")
                .header("Authorization", mockToken)
                .param("date", LocalDate.of(2024, 9, 1).toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("daily-question/get-daily-question-answer",
                        queryParameters(
                                parameterWithName("date").description("조회할 날짜 (YYYY-MM-DD 형식)")
                        ),
                        responseFields(
                                fieldWithPath("question").description("해당 날짜의 질문"),
                                fieldWithPath("answer").description("사용자의 답변")
                        )
                ));
    }

    @Test
    @DisplayName("API - 오늘의 질문 답변 수정")
    void updateDailyQuestionAnswerTest() throws Exception {
        //given
        DailyQuestionAnswerRequest request = new DailyQuestionAnswerRequest("수정할 답변");

        //when & then
        mockMvc.perform(put("/api/daily-question/answer")
                .header("Authorization", mockToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(document("daily-question/update-daily-question-answer",
                        requestFields(
                                fieldWithPath("answer").description("수정할 답변")
                        )
                ));
    }
}