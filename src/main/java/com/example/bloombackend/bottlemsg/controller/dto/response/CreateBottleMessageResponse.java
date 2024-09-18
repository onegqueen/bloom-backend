package com.example.bloombackend.bottlemsg.controller.dto.response;

public record CreateBottleMessageResponse(
	Long id
) {
	public static CreateBottleMessageResponse of(Long id) {
		return new CreateBottleMessageResponse(id);
	}
}
