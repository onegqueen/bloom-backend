package com.example.bloombackend.bottlemsg.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.bloombackend.bottlemsg.controller.dto.request.CreateBottleMessageReactionRequest;
import com.example.bloombackend.bottlemsg.controller.dto.request.CreateBottleMessageRequest;
import com.example.bloombackend.bottlemsg.controller.dto.response.BottleMessageReactionResponse;
import com.example.bloombackend.bottlemsg.controller.dto.response.BottleMessageWithReactionResponse;
import com.example.bloombackend.bottlemsg.controller.dto.response.CreateBottleMessageResponse;
import com.example.bloombackend.bottlemsg.controller.dto.response.UserBottleMessagesResponse;
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

	@GetMapping("")
	public ResponseEntity<UserBottleMessagesResponse> getUserBottleMessages(@CurrentUser Long userId) {
		return ResponseEntity.ok(bottleMessageService.getUserBottleMessages(userId));
	}

	@GetMapping("/{messageId}")
	public ResponseEntity<BottleMessageWithReactionResponse> getBottleMessage(@CurrentUser Long userId, @PathVariable Long messageId) {
		return ResponseEntity.ok(bottleMessageService.getBottleMessage(messageId,userId));
	}

	@GetMapping("/random")
	public ResponseEntity<BottleMessageWithReactionResponse> createBottleMessageRandom(@CurrentUser Long userId) {
		return ResponseEntity.ok(bottleMessageService.getRandomBottleMessage(userId));
	}

	@PostMapping("/{messageId}/react")
	public ResponseEntity<BottleMessageReactionResponse> reactBottleMessage(
		@CurrentUser Long userId,
		@RequestBody CreateBottleMessageReactionRequest request,
		@PathVariable Long messageId) {
		return ResponseEntity.ok(bottleMessageService.updateBottleMessageReaction(messageId, userId,request));
	}

	@PostMapping("/{messageId}/delete")
	public ResponseEntity<UserBottleMessagesResponse> deleteBottleMessage(
		@CurrentUser Long userId,
		@PathVariable Long messageId) {
		return ResponseEntity.ok(bottleMessageService.deleteBottleMessage(userId, messageId));
	}

	@GetMapping("/sent")
	public ResponseEntity<UserBottleMessagesResponse> sentBottleMessages(@CurrentUser Long userId) {
		return ResponseEntity.ok(bottleMessageService.getSentBottleMessages(userId));
	}

	@DeleteMapping("/{messageId}/react")
	public ResponseEntity<Void> deleteBottleMessageReaction(
			@CurrentUser Long userId,
			@PathVariable Long messageId,
			@RequestBody CreateBottleMessageReactionRequest request
	){
		bottleMessageService.deleteBottleMessageReaction(messageId,userId, request.reaction());
		return ResponseEntity.ok().build();
	}
}
