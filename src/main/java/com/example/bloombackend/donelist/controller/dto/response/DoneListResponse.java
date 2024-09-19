package com.example.bloombackend.donelist.controller.dto.response;

import java.util.List;

public record DoneListResponse(
	String date,
	List<DoneItemResponse> donelist
) {
}
