package com.example.bloombackend.quest.controller.dto.response;

public record UserQuestLogResponse(Long id, String iconUrl, String title, int maxCount, boolean isDone) {
}