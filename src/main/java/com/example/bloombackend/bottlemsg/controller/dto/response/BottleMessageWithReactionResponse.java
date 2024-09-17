package com.example.bloombackend.bottlemsg.controller.dto.response;

public record BottleMessageWithReactionResponse(
        BottleMessageLogResponse log,
        BottleMessageResponse message,
        BottleMessageReactionResponse reaction
) {
}
