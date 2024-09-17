package com.example.bloombackend.achievement.repository.querydsl;

import com.example.bloombackend.achievement.controller.dto.response.MonthlyAchievementResponse;

import java.util.List;

public interface DailyAchievementRepositoryCustom {
    List<MonthlyAchievementResponse> getRecentSixMonthsAchievements(Long userId);
}