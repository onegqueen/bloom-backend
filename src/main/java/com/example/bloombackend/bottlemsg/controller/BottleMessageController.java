package com.example.bloombackend.bottlemsg.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloombackend.bottlemsg.controller.dto.request.CreateBottleMessageRequest;
import com.example.bloombackend.bottlemsg.controller.dto.response.CreateBottleMessageResponse;
import com.example.bloombackend.bottlemsg.sevice.BottleMessageService;
import com.example.bloombackend.global.config.annotation.CurrentUser;

@RestController
@RequestMapping("/api/bottle-messages")
public class BottleMessageController {
	private final BottleMessageService bottleMessageService;

	public BottleMessageController(BottleMessageService bottleMessageService) {
		this.bottleMessageService = bottleMessageService;
	}

	@PostMapping("")
	public ResponseEntity<CreateBottleMessageResponse> createBottleMessage(
		@CurrentUser Long userId,
		@RequestBody CreateBottleMessageRequest request) {
		return ResponseEntity.ok(bottleMessageService.createBottleMessage(userId, request));
	}
}
