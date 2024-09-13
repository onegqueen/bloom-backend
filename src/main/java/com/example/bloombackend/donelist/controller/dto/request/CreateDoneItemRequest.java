package com.example.bloombackend.donelist.controller.dto.request;

import java.util.List;

public record CreateDoneItemRequest(
	String iconUrl,
	String title,
	String content,
	List<String> photos
) {
}
