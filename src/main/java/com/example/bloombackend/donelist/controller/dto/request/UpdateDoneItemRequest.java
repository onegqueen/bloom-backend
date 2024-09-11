package com.example.bloombackend.donelist.controller.dto.request;

public record UpdateDoneItemRequest(
	Long itemId,
	String iconUrl,
	String title,
	String content,
	String photoUrl
) {
}
