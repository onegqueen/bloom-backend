package com.example.bloombackend.bottlemsg.controller.dto.response;

public record BottleMessageWithDateLogResponse(
	BottleMessageLogResponse log,
	Long messageId,
	String title,
	String postCardUrl,
	String negativity) {
	public static BottleMessageWithDateLogResponse of(BottleMessageLogResponse log, BottleMessageResponse response) {
		return new BottleMessageWithDateLogResponse(log, response.messageId(), response.title(),
			response.postCardUrl(), response.negativity());
	}
}

