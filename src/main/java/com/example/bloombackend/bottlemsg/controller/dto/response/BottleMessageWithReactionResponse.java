package com.example.bloombackend.bottlemsg.controller.dto.response;

public record BottleMessageWithReactionResponse(
	BottleMessageResponse message,
	BottleMessageReactionResponse reaction
) {
}
