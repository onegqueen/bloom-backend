package com.example.bloombackend.donelist.controller.dto.response;

public record DoneItemSummaryResponse(
	Long itemId,
	String iconUrl,
	String title
) {
}
