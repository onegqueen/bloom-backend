package com.example.bloombackend.restdocs;

import com.example.bloombackend.achievement.controller.dto.request.AchievementLevelUpdateRequest;
import com.example.bloombackend.achievement.controller.dto.request.FlowerRegisterRequest;
import com.example.bloombackend.achievement.entity.DailyAchievementEntity;
import com.example.bloombackend.achievement.entity.FlowerEntity;
import com.example.bloombackend.achievement.repository.DailyAchievementRepository;
import com.example.bloombackend.achievement.repository.FlowerRepository;
import com.example.bloombackend.global.AIUtil;
import com.example.bloombackend.global.config.JwtTokenProvider;
import com.example.bloombackend.oauth.OAuthProvider;
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

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyString;
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
public class AchievementRestDocsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FlowerRepository flowerRepository;

    @Autowired
    private DailyAchievementRepository dailyAchievementRepository;

    @Autowired
    private UserRepository userRepository;

    @SpyBean
    private JwtTokenProvider jwtTokenProvider;

    @SpyBean
    private AIUtil aiUtil;

    private UserEntity testUser;

    private FlowerEntity flowerEntity;

    private String mockToken;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        objectMapper = new ObjectMapper();
        mockToken = "jwtToken";
        testUser = userRepository.save(new UserEntity(OAuthProvider.KAKAO, "testUser", "testId"));
        doReturn(testUser.getId()).when(jwtTokenProvider).getUserIdFromToken(mockToken);

        // 외부 API 호출의 경우 테스트 시 Mocking 처리
        doReturn("AI 총평 데이터").when(aiUtil).generateCompletion(anyString());

        // 테스트 용 꽃 엔티티 등록
        flowerEntity = new FlowerEntity("https://test.com/flower1.png", "튤립");
        flowerRepository.save(flowerEntity);

        // 7월 성취도 기록 생성
        for (int day = 1; day <= 10; day++) {
            DailyAchievementEntity achievement = new DailyAchievementEntity(testUser, flowerEntity);
            createAchievement(achievement, LocalDateTime.of(2024, 7, day, 0, 0), 9);  // 7월 1일부터 5일까지 성취도 9
        }

        // 8월 성취도 기록 생성
        for (int day = 1; day <= 15; day++) {
            DailyAchievementEntity achievement = new DailyAchievementEntity(testUser, flowerEntity);
            createAchievement(achievement, LocalDateTime.of(2024, 8, day, 0, 0), 9);  // 8월 1일부터 5일까지 성취도 9
        }

        // 9월 성취도 기록 생성
        DailyAchievementEntity achievement1 = new DailyAchievementEntity(testUser, flowerEntity);
        DailyAchievementEntity achievement2 = new DailyAchievementEntity(testUser, flowerEntity);
        DailyAchievementEntity achievement3 = new DailyAchievementEntity(testUser, flowerEntity);
        createAchievement(achievement1, LocalDateTime.of(2024, 9, 1, 0, 0), 4);
        createAchievement(achievement2, LocalDateTime.of(2024, 9, 2, 0, 0), 8);
        createAchievement(achievement3, LocalDateTime.of(2024, 9, 7, 0, 0), 9);
    }

    @Test
    @DisplayName("API - 오늘의 꽃 등록")
    void setDailyFlowerTest() throws Exception {
        //given
        FlowerRegisterRequest request = new FlowerRegisterRequest(flowerEntity.getId());

        //when & then
        mockMvc.perform(post("/api/achievement/flower")
                .header("Authorization", mockToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(document("achievement/set-daily-flower",
                        requestFields(
                                fieldWithPath("flowerId").description("등록할 꽃 ID")
                        )
                ));
    }

    @Test
    @DisplayName("API - 성취도 레벨 업데이트")
    void updateAchievementLevelTest() throws Exception {
        //given
        dailyAchievementRepository.save(new DailyAchievementEntity(testUser, flowerEntity));
        AchievementLevelUpdateRequest request = new AchievementLevelUpdateRequest(1);

        //when & then
        mockMvc.perform(patch("/api/achievement")
                .header("Authorization", mockToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(document("achievement/update-achievement-level",
                        requestFields(
                                fieldWithPath("increaseBy").description("증가 시킬 성취도 단계 값")
                        ),
                        responseFields(
                                fieldWithPath("updatedLevel").description("업데이트된 성취도 단계")
                        )
                ));
    }

    @Test
    @DisplayName("API - 월간 성취도 조회")
    void getMonthlyAchievementsTest() throws Exception {
        //when & then
        mockMvc.perform(get("/api/achievement/monthly")
                .header("Authorization", mockToken)
                .param("month", "2024-09"))
                .andExpect(status().isOk())
                .andDo(document("achievement/get-monthly-achievements",
                        queryParameters(
                                parameterWithName("month").description("조회할 월 (YYYY-MM)")
                        ),
                        responseFields(
                                fieldWithPath("dailyData[]").description("일별 데이터"),
                                fieldWithPath("dailyData[].date").description("날짜"),
                                fieldWithPath("dailyData[].flowerIconUrl").description("설정한 꽃 아이콘 URL"),
                                fieldWithPath("dailyData[].achievementLevel").description("성취 단계")
                        )
                ));
    }

    @Test
    @DisplayName("API - 최근 6개월 성취도 조회")
    void getRecentSixMonthsAchievementsTest() throws Exception {
        //when & then
        mockMvc.perform(get("/api/achievement/recent-six-months")
                .header("Authorization", mockToken))
                .andExpect(status().isOk())
                .andDo(document("achievement/get-recent-six-months-achievements",
                        responseFields(
                                fieldWithPath("monthlyData[]").description("월별 데이터"),
                                fieldWithPath("monthlyData[].month").description("조회된 월"),
                                fieldWithPath("monthlyData[].bloomed").description("꽃이 핀 횟수"),
                                fieldWithPath("averageBloomed").description("평균 꽃이 핀 횟수"),
                                fieldWithPath("aiSummary").description("AI 총평")
                        )
                ));
    }

    @Test
    @DisplayName("API - 오늘의 꽃 조회")
    void getDailyFlowerTest() throws Exception {
        //given
        dailyAchievementRepository.save(new DailyAchievementEntity(testUser, flowerEntity));

        //when & then
        mockMvc.perform(get("/api/achievement/flower")
                .header("Authorization", mockToken))
                .andExpect(status().isOk())
                .andDo(document("achievement/get-daily-flower",
                        responseFields(
                                fieldWithPath("flowerId").description("오늘의 꽃 ID"),
                                fieldWithPath("iconUrl").description("오늘의 꽃 아이콘 URL")
                        )
                ));
    }

    @Test
    @DisplayName("API - 오늘의 성취도 조회")
    void getTodayAchievementTest() throws Exception {
        //given
        dailyAchievementRepository.save(new DailyAchievementEntity(testUser, flowerEntity));

        //when & then
        mockMvc.perform(get("/api/achievement")
                .header("Authorization", mockToken))
                .andExpect(status().isOk())
                .andDo(document("achievement/get-today-achievement",
                        responseFields(
                                fieldWithPath("date").description("날짜"),
                                fieldWithPath("flowerIconUrl").description("설정한 꽃 아이콘 URL"),
                                fieldWithPath("achievementLevel").description("성취 단계")
                        )
                ));
    }

    private void createAchievement(DailyAchievementEntity achievement, LocalDateTime dateTime, int achievementLevel) throws Exception {
        Field createdAtField = DailyAchievementEntity.class.getDeclaredField("createdAt");
        createdAtField.setAccessible(true);
        createdAtField.set(achievement, dateTime);
        achievement.increaseAchievementLevel(achievementLevel);
        dailyAchievementRepository.save(achievement);
    }
}