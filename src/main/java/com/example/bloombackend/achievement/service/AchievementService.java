package com.example.bloombackend.achievement.service;

import com.example.bloombackend.achievement.controller.dto.request.AchievementLevelUpdateRequest;
import com.example.bloombackend.achievement.controller.dto.request.FlowerRegisterRequest;
import com.example.bloombackend.achievement.controller.dto.response.*;
import com.example.bloombackend.achievement.controller.response.DailyFlowerResponse;
import com.example.bloombackend.achievement.entity.DailyAchievementEntity;
import com.example.bloombackend.achievement.entity.FlowerEntity;
import com.example.bloombackend.achievement.repository.DailyAchievementRepository;
import com.example.bloombackend.achievement.repository.FlowerRepository;
import com.example.bloombackend.user.entity.UserEntity;
import com.example.bloombackend.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AchievementService {

    private final DailyAchievementRepository dailyAchievementRepository;
    private final FlowerRepository flowerRepository;
    private final UserRepository userRepository;

    public AchievementService(DailyAchievementRepository dailyAchievementRepository, FlowerRepository flowerRepository, UserRepository userRepository) {
        this.dailyAchievementRepository = dailyAchievementRepository;
        this.flowerRepository = flowerRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void setDailyFlower(Long userId, FlowerRegisterRequest request) {
        UserEntity user = getUserEntity(userId);
        FlowerEntity flower = getFlowerEntity(request);
        dailyAchievementRepository.save(new DailyAchievementEntity(user, flower));
    }

    private FlowerEntity getFlowerEntity(FlowerRegisterRequest request) {
        return flowerRepository.findById(request.flowerId())
                .orElseThrow(() -> new EntityNotFoundException("Flower not found:" + request.flowerId()));
    }

    private UserEntity getUserEntity(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found:" + userId));
    }

    @Transactional
    public AchievementLevelUpdateResponse updateAchievementLevel(Long userId, AchievementLevelUpdateRequest request) {
        DailyAchievementEntity dailyAchievement = getDailyAchievementEntity(userId);
        dailyAchievement.increaseAchievementLevel(request.increaseBy());
        dailyAchievementRepository.save(dailyAchievement);
        return new AchievementLevelUpdateResponse(dailyAchievement.getAchievementLevel());
    }

    @Transactional(readOnly = true)
    public MonthlyDataResponse getMonthlyAchievements(Long userId, String month) {
        List<DailyAchievementResponse> dailyData = getMonthlyAchievementEntities(userId, month).stream()
                .map(DailyAchievementEntity::toDto)
                .toList();
        return new MonthlyDataResponse(dailyData);
    }

    private DailyAchievementEntity getDailyAchievementEntity(Long userId) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);
        return dailyAchievementRepository.findFirstByUserIdAndCreatedAtBetween(userId, startOfDay, endOfDay)
                .orElseThrow(() -> new EntityNotFoundException("Daily achievement not found for user:" + userId));
    }

    private List<DailyAchievementEntity> getMonthlyAchievementEntities(Long userId, String month) {
        LocalDateTime startOfMonth = LocalDate.parse(month + "-01").atStartOfDay();
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);
        return dailyAchievementRepository.findByUserIdAndCreatedAtBetween(userId, startOfMonth, endOfMonth);
    }

    @Transactional(readOnly = true)
    public RecentSixMonthDataResponse getRecentSixMonthsAchievements(Long userId) {
        List<MonthlyAchievementResponse> monthlyAchievements = dailyAchievementRepository.getRecentSixMonthsAchievements(userId);
        return new RecentSixMonthDataResponse(monthlyAchievements, getAverageBloomed(monthlyAchievements));
    }

    private double getAverageBloomed(List<MonthlyAchievementResponse> monthlyAchievements) {
        return monthlyAchievements.stream()
                .mapToInt(MonthlyAchievementResponse::bloomed)
                .average()
                .orElse(0);
    }

    public DailyFlowerResponse getDailyFlower(Long userId) {
        return dailyAchievementRepository.getDailyFlower(userId);
    }
}