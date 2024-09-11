package com.example.bloombackend.donelist.controller.dto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloombackend.donelist.controller.dto.request.CreateDoneItemRequest;
import com.example.bloombackend.donelist.controller.dto.response.DoneItemDetailResponse;
import com.example.bloombackend.donelist.service.DoneListService;
import com.example.bloombackend.global.config.annotation.CurrentUser;

@RestController
@RequestMapping("/api/done-list")
public class DoneListController {
	private final DoneListService doneListService;

	public DoneListController(DoneListService doneListService) {
		this.doneListService = doneListService;
	}

	@PostMapping
	public ResponseEntity<DoneItemDetailResponse> createDoneItem(
		@CurrentUser Long userId,
		@RequestBody CreateDoneItemRequest request) {
		return ResponseEntity.ok(doneListService.createDoneItem(userId, request));
	}
}
