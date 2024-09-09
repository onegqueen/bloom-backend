package com.example.bloombackend.bottlemsg.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bloombackend.bottlemsg.controller.dto.request.CreateBottleMessageRequest;
import com.example.bloombackend.bottlemsg.entity.BottleMessageEntity;
import com.example.bloombackend.bottlemsg.repository.BottleMessageRepository;

@Service
public class BottleMessageService {
	private final BottleMessageRepository bottleMessageRepository;

	@Autowired
	public BottleMessageService(BottleMessageRepository bottleMessageRepository) {
		this.bottleMessageRepository = bottleMessageRepository;
	}

	@Transactional
	public Long createBottleMessage(CreateBottleMessageRequest request) {
		return bottleMessageRepository.save(
			BottleMessageEntity.builder()
				.content(request.content())
				.title(request.title())
				.build()
		).getId();
	}
}
