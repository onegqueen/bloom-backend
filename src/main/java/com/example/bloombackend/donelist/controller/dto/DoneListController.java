package com.example.bloombackend.donelist.controller.dto;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
		@RequestPart("data") CreateDoneItemRequest request,
		@RequestPart(value = "files", required = false) List<MultipartFile> photos) {
		return ResponseEntity.ok(doneListService.createDoneItem(userId, request, photos));
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
		@PathVariable Long itemId,
		@RequestPart("data") UpdateDoneItemRequest request,
		@RequestPart(value = "files", required = false) List<MultipartFile> updatedPhotoFiles
	) {
		return ResponseEntity.ok(doneListService.updateDoneItem(itemId, request, updatedPhotoFiles));
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
