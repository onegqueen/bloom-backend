package com.example.bloombackend.restdocs;

import com.example.bloombackend.global.config.JwtTokenProvider;
import com.example.bloombackend.oauth.OAuthProvider;
import com.example.bloombackend.quest.controller.dto.request.QuestRegisterRequest;
import com.example.bloombackend.quest.entity.QuestEntity;
import com.example.bloombackend.quest.entity.UserQuestLogEntity;
import com.example.bloombackend.quest.repository.QuestRepository;
import com.example.bloombackend.quest.repository.UserQuestLogRepository;
import com.example.bloombackend.user.entity.UserEntity;
import com.example.bloombackend.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
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

import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
public class QuestRestDocsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserQuestLogRepository userQuestLogRepository;

    @SpyBean
    private JwtTokenProvider jwtTokenProvider;

    private UserEntity testUser;

    private String mockToken;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockToken = "jwtToken";
        testUser = userRepository.save(new UserEntity(OAuthProvider.KAKAO, "testUser", "testId"));
        doReturn(testUser.getId()).when(jwtTokenProvider).getUserIdFromToken(mockToken);
        questRepository.save(new QuestEntity("https://test.com/icon1.png", "물 마시기", 10));
        questRepository.save(new QuestEntity("https://test.com/icon2.png", "산책 하기", 1));
    }

    @Test
    @DisplayName("API - 사용 가능한 퀘스트 목록 조회")
    void getAvailableQuestsTest() throws Exception {
        //when & then
        mockMvc.perform(get("/api/quests/available")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("quest/get-available-quests",
                        responseFields(
                                fieldWithPath("quests[]").description("사용 가능한 퀘스트 목록"),
                                fieldWithPath("quests[].id").description("퀘스트 ID"),
                                fieldWithPath("quests[].title").description("퀘스트 제목"),
                                fieldWithPath("quests[].iconUrl").description("퀘스트 아이콘 URL"),
                                fieldWithPath("quests[].maxCount").description("퀘스트 최대 수행 횟수")
                        )
                ));
    }

    @Test
    @DisplayName("API - 오늘의 퀘스트 등록")
    void registerQuestsTest() throws Exception {
        //given
        QuestRegisterRequest request = new QuestRegisterRequest(List.of(1L, 2L));

        //when & then
        mockMvc.perform(post("/api/quests")
                .header("Authorization", mockToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(document("quest/register-today-quests",
                        requestFields(
                                fieldWithPath("questIds").description("등록할 퀘스트 ID 목록")
                        )
                ));
    }

    @Test
    @DisplayName("API - 사용자가 등록한 오늘의 퀘스트 목록 조회")
    void getRegisteredQuestsTest() throws Exception {
        //given
        UserQuestLogEntity log1 = new UserQuestLogEntity(testUser, questRepository.findById(1L).orElseThrow());
        UserQuestLogEntity log2 = new UserQuestLogEntity(testUser, questRepository.findById(2L).orElseThrow());
        userQuestLogRepository.saveAll(List.of(log1, log2));

        //when & then
        mockMvc.perform(get("/api/quests/registered")
                .header("Authorization", mockToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("quest/get-registered-today-quests",
                        responseFields(
                                fieldWithPath("quests[]").description("등록한 퀘스트 목록"),
                                fieldWithPath("quests[].id").description("퀘스트 ID"),
                                fieldWithPath("quests[].title").description("퀘스트 제목"),
                                fieldWithPath("quests[].iconUrl").description("퀘스트 아이콘 URL"),
                                fieldWithPath("quests[].maxCount").description("퀘스트 최대 수행 횟수"),
                                fieldWithPath("quests[].isDone").description("퀘스트 완료 여부")
                        )
                ));
    }

    @Test
    @DisplayName("API - 퀘스트 등록 해제")
    void unregisterQuestsTest() throws Exception {
        //given
        UserQuestLogEntity log = new UserQuestLogEntity(testUser, questRepository.findById(1L).orElseThrow());
        userQuestLogRepository.save(log);

        //when & then
        mockMvc.perform(delete("/api/quests/{questId}", 1L)
                .header("Authorization", mockToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("quest/unregister-quests",
                        pathParameters(
                                parameterWithName("questId").description("해제할 퀘스트 ID")
                        )
                ));
    }
}