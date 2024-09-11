package com.example.bloombackend.donelist.controller.dto.response;

import java.util.List;

public record DoneListResponse(
	List<DoneItemSummaryResponse> donelist
) {
}
