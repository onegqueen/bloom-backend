package com.example.bloombackend.bottlemsg.controller.dto.response;

public record BottleMessageReactionResponse(
	int like,
	int empathy,
	int cheer
) {
}
