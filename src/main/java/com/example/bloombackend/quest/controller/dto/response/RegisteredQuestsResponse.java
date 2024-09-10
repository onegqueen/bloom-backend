package com.example.bloombackend.quest.controller.dto.response;

import java.util.List;

public record RegisteredQuestsResponse(List<UserQuestLogResponse> quests) {
}