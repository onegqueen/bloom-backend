package com.example.bloombackend.dailyquestion.controller;

import com.example.bloombackend.dailyquestion.controller.request.DailyQuestionAnswerRequest;
import com.example.bloombackend.dailyquestion.controller.response.DailyQuestionAnswerResponse;
import com.example.bloombackend.dailyquestion.controller.response.DailyQuestionResponse;
import com.example.bloombackend.dailyquestion.service.DailyQuestionService;
import com.example.bloombackend.global.config.annotation.CurrentUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/daily-question")
public class DailyQuestionController {
    private final DailyQuestionService dailyQuestionService;

    public DailyQuestionController(DailyQuestionService dailyQuestionService) {
        this.dailyQuestionService = dailyQuestionService;
    }

    @GetMapping("")
    public ResponseEntity<DailyQuestionResponse> getDailyQuestion(@CurrentUser Long userId) {
        return ResponseEntity.ok(dailyQuestionService.getDailyQuestion(userId));
    }

    @GetMapping("/answer")
    public ResponseEntity<DailyQuestionAnswerResponse> getDailyQuestionAnswer(@CurrentUser Long userId, @RequestParam String date) {
        return ResponseEntity.ok(dailyQuestionService.getDailyQuestionAnswer(userId, date));
    }

    @PutMapping("/answer")
    public ResponseEntity<Void> updateDailyQuestionAnswer(@CurrentUser Long userId, @RequestBody DailyQuestionAnswerRequest request) {
        dailyQuestionService.updateDailyQuestionAnswer(userId, request);
        return ResponseEntity.ok().build();
    }
}