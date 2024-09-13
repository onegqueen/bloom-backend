package com.example.bloombackend.donelist.controller.dto.request;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public record UpdateDoneItemRequest(
	Long itemId,
	Optional<String> iconUrl,
	Optional<String> title,
	Optional<String> content,
	List<Long> deletedPhotoIds,
	List<MultipartFile> updatedPhotoFiles
) {
	public UpdateDoneItemRequest {
		deletedPhotoIds = (deletedPhotoIds == null) ? Collections.emptyList() : deletedPhotoIds;
		updatedPhotoFiles = (updatedPhotoFiles == null) ? Collections.emptyList() : updatedPhotoFiles;
	}
}
