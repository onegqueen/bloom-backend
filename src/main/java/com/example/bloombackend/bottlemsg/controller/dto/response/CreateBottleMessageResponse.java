package com.example.bloombackend.bottlemsg.controller.dto.response;

import com.example.bloombackend.claude.dto.SentimentAnalysisDto;

public record CreateBottleMessageResponse(
	Long id,
	SentimentAnalysisDto analysis
) {
	public static CreateBottleMessageResponse of(Long id, SentimentAnalysisDto analyze) {
		return new CreateBottleMessageResponse(id, analyze);
	}
}
