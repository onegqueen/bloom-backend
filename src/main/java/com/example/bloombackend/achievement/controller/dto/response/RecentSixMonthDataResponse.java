package com.example.bloombackend.achievement.controller.dto.response;

import java.util.List;

public record RecentSixMonthDataResponse(List<MonthlyAchievementResponse> monthlyData, double averageBloomed, String aiSummary) {
}