package com.example.bloombackend.donelist.controller.dto.response;

import java.util.List;

public record DoneItemDetailResponse(
	DoneItemResponse doneItem,
	List<DoneItemPhotoResponse> photoUrls
) {
}
