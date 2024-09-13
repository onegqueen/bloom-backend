package com.example.bloombackend.donelist.controller.dto.request;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public record UpdateDoneItemRequest(
	Long itemId,
	Optional<String> iconUrl,
	Optional<String> title,
	Optional<String> content,
	List<Long> deletedPhotoIds,
	List<String> updatedPhotoFiles
) {
	public UpdateDoneItemRequest {
		deletedPhotoIds = (deletedPhotoIds == null) ? Collections.emptyList() : deletedPhotoIds;
		updatedPhotoFiles = (updatedPhotoFiles == null) ? Collections.emptyList() : updatedPhotoFiles;
	}
}