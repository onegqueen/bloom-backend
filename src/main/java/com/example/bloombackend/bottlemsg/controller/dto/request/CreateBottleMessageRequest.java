package com.example.bloombackend.bottlemsg.controller.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record CreateBottleMessageRequest(
	String title,
	String content,
	MultipartFile postCard
) {
}
