package com.example.bloombackend.donelist.controller.dto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloombackend.donelist.controller.dto.request.CreateDoneItemRequest;
import com.example.bloombackend.donelist.controller.dto.request.UpdateDoneItemRequest;
import com.example.bloombackend.donelist.controller.dto.response.DoneItemDetailResponse;
import com.example.bloombackend.donelist.controller.dto.response.DoneListResponse;
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

	@GetMapping("/detail/{itemId}")
	public ResponseEntity<DoneItemDetailResponse> getDoneItem(
		@CurrentUser Long userId,
		@PathVariable Long itemId) {
		return ResponseEntity.ok(doneListService.getDoneItem(itemId));
	}

	@GetMapping("/{date}")
	public ResponseEntity<DoneListResponse> getDoneItemByDate(
		@CurrentUser Long userId,
		@PathVariable String date
	) {
		return ResponseEntity.ok(doneListService.getDoneListByDate(userId, date));
	}

	@PutMapping("/{itemId}")
	public ResponseEntity<DoneItemDetailResponse> updateDoneItem(
		@CurrentUser Long userId,
		@RequestBody UpdateDoneItemRequest request
	) {
		return ResponseEntity.ok(doneListService.updateDoneItem(request));
	}

	@DeleteMapping("/{itemId}")
	public ResponseEntity<Void> deleteDoneItem(
		@CurrentUser Long userId,
		@PathVariable Long itemId
	) {
		doneListService.deleteDoneItem(itemId);
		return ResponseEntity.noContent().build();
	}

}
