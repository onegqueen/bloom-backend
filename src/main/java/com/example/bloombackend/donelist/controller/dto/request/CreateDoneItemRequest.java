package com.example.bloombackend.donelist.controller.dto.request;

public record CreateDoneItemRequest(
	String iconUrl,
	String title,
	String content,
	String doneDate
) {
}
