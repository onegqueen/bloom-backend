package com.example.bloombackend.donelist.controller.dto.response;

import lombok.Builder;

@Builder
public record DoneItemDetailResponse(
	Long itemId,
	String iconUrl,
	String title,
	String content,
	String photoUrl
) {
}
