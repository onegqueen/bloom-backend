package com.example.bloombackend.bottlemsg.controller.dto.response;

public record BottleMessageReactionResponse( boolean isReacted, int like, int empathy, int cheer
) {
}
