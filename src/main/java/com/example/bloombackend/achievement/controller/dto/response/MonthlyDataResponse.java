package com.example.bloombackend.achievement.controller.dto.response;

import java.util.List;

public record MonthlyDataResponse(List<DailyAchievementResponse> dailyData) {
}