package com.example.bloombackend.claude.dto;

import java.util.List;

public record SentimentAnalysisDto(List<EmotionScore> emotions, String negativeImpact) {
}
