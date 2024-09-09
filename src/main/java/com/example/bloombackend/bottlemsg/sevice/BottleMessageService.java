package com.example.bloombackend.bottlemsg.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bloombackend.bottlemsg.controller.dto.request.CreateBottleMessageRequest;
import com.example.bloombackend.bottlemsg.controller.dto.response.CreateBottleMessageResponse;
import com.example.bloombackend.bottlemsg.entity.BottleMessageEntity;
import com.example.bloombackend.bottlemsg.repository.BottleMessageRepository;
import com.example.bloombackend.user.service.UserService;

@Service
public class BottleMessageService {
	private final BottleMessageRepository bottleMessageRepository;
	private final UserService userService;

	@Autowired
	public BottleMessageService(BottleMessageRepository bottleMessageRepository, UserService userService) {
		this.bottleMessageRepository = bottleMessageRepository;
		this.userService = userService;
	}

	@Transactional
	public CreateBottleMessageResponse createBottleMessage(Long userId, CreateBottleMessageRequest request) {
		return CreateBottleMessageResponse.of(bottleMessageRepository.save(
			BottleMessageEntity.builder()
				.content(request.content())
				.user(userService.findUserById(userId))
				.title(request.title())
				.postcardUrl(request.postCard().toString())
				.build()
		).getId());
	}
}
