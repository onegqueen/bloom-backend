package com.example.bloombackend.quest.controller.dto.request;

import java.util.List;

public record QuestRegisterRequest(List<Long> questIds) {
}