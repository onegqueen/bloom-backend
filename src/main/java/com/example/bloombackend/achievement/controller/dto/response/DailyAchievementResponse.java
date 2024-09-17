package com.example.bloombackend.achievement.controller.dto.response;

import java.time.LocalDate;

public record DailyAchievementResponse(LocalDate date, String flowerIconUrl, int achievementLevel) {
}