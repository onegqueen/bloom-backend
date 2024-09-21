package com.example.bloombackend.achievement.controller;

import com.example.bloombackend.achievement.controller.dto.request.AchievementLevelUpdateRequest;
import com.example.bloombackend.achievement.controller.dto.request.FlowerRegisterRequest;
import com.example.bloombackend.achievement.controller.dto.response.AchievementLevelUpdateResponse;
import com.example.bloombackend.achievement.controller.dto.response.DailyAchievementResponse;
import com.example.bloombackend.achievement.controller.dto.response.MonthlyDataResponse;
import com.example.bloombackend.achievement.controller.dto.response.RecentSixMonthDataResponse;
import com.example.bloombackend.achievement.controller.response.DailyFlowerResponse;
import com.example.bloombackend.achievement.service.AchievementService;
import com.example.bloombackend.global.config.annotation.CurrentUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/achievement")
public class AchievementController {
    private final AchievementService achievementService;

    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @PostMapping("/flower")
    public ResponseEntity<Void> setDailyFlower(@RequestBody FlowerRegisterRequest request,
                                                 @CurrentUser Long userId) {
        achievementService.setDailyFlower(userId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/flower")
    public ResponseEntity<DailyFlowerResponse> getDailyFlower(@CurrentUser Long userId) {
        return ResponseEntity.ok(achievementService.getDailyFlower(userId));
    }

    @PatchMapping("")
    public ResponseEntity<AchievementLevelUpdateResponse> updateAchievementLevel(@RequestBody AchievementLevelUpdateRequest request,
                                                                                 @CurrentUser Long userId) {
        return ResponseEntity.ok(achievementService.updateAchievementLevel(userId, request));
    }

    @GetMapping("/monthly")
    public ResponseEntity<MonthlyDataResponse> getMonthlyAchievements(@RequestParam String month,
                                                                      @CurrentUser Long userId) {
        return ResponseEntity.ok(achievementService.getMonthlyAchievements(userId, month));
    }

    @GetMapping("/recent-six-months")
    public ResponseEntity<RecentSixMonthDataResponse> getRecentSixMonthsAchievements(@CurrentUser Long userId) {
        return ResponseEntity.ok(achievementService.getRecentSixMonthsAchievements(userId));
    }

    @GetMapping("")
    public ResponseEntity<DailyAchievementResponse> getTodayAchievement(@CurrentUser Long userId) {
        return ResponseEntity.ok(achievementService.getDailyAchievement(userId));
    }
}