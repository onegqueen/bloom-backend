package com.example.bloombackend.donelist.controller.dto.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public record CreateDoneItemRequest(
	String iconUrl,
	String title,
	String content,
	List<MultipartFile> photos
) {
}
