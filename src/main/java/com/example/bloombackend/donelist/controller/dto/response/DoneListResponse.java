package com.example.bloombackend.donelist.controller.dto.response;

import java.time.LocalDate;
import java.util.List;

public record DoneListResponse(
	LocalDate date,
	List<DoneItemResponse> donelist
) {
}
