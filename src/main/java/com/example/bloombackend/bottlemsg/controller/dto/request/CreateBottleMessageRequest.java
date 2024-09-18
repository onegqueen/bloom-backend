package com.example.bloombackend.bottlemsg.controller.dto.request;

public record CreateBottleMessageRequest(
	String title,
	String content,
	String postCard
) {
}
