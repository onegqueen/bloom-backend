package com.example.bloombackend.quest.controller.dto;

import com.example.bloombackend.global.config.annotation.CurrentUser;
import com.example.bloombackend.quest.controller.dto.request.QuestRegisterRequest;
import com.example.bloombackend.quest.controller.dto.response.AvailableQuestsResponse;
import com.example.bloombackend.quest.controller.dto.response.RegisteredQuestsResponse;
import com.example.bloombackend.quest.service.QuestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quests")
public class QuestController {
    private final QuestService questService;

    public QuestController(QuestService questService) {
        this.questService = questService;
    }

    @GetMapping("/available")
    public ResponseEntity<AvailableQuestsResponse> getAvailableQuests() {
        return ResponseEntity.ok(questService.getAvailableQuests());
    }

    @PostMapping("")
    public ResponseEntity<Void> registerQuests(@CurrentUser Long userId, @RequestBody QuestRegisterRequest request) {
        questService.registerQuests(userId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/registered")
    public ResponseEntity<RegisteredQuestsResponse> getRegisteredQuests(@CurrentUser Long userId) {
        return ResponseEntity.ok(questService.getRegisteredQuestsForToday(userId));
    }

    @PatchMapping("/{questId}/complete")
    public ResponseEntity<Void> completeQuest(@CurrentUser Long userId, @PathVariable Long questId) {
        questService.completeQuest(userId, questId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{questId}")
    public ResponseEntity<Void> unregisterQuest(@CurrentUser Long userId, @PathVariable Long questId) {
        questService.unregisterQuest(userId, questId);
        return ResponseEntity.ok().build();
    }
}