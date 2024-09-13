package com.example.bloombackend.quest.controller.dto.response;

import java.util.List;

public record AvailableQuestsResponse(List<QuestResponse> quests) {
}