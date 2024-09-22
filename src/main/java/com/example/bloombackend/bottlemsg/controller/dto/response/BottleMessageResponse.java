package com.example.bloombackend.bottlemsg.controller.dto.response;

import lombok.Builder;

@Builder
public record BottleMessageResponse(
	Long messageId,
	String title,
	String content,
	String postCardUrl,
	String negativity
) {
}
