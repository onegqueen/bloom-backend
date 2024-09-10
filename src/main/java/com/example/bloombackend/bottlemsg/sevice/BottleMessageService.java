package com.example.bloombackend.bottlemsg.sevice;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bloombackend.bottlemsg.controller.dto.request.CreateBottleMessageRequest;
import com.example.bloombackend.bottlemsg.controller.dto.response.BottleMessageReactionResponse;
import com.example.bloombackend.bottlemsg.controller.dto.response.BottleMessageWithReactionResponse;
import com.example.bloombackend.bottlemsg.controller.dto.response.CreateBottleMessageResponse;
import com.example.bloombackend.bottlemsg.controller.dto.response.UserBottleMessagesResponse;
import com.example.bloombackend.bottlemsg.entity.BottleMessageEntity;
import com.example.bloombackend.bottlemsg.entity.BottleMessageReceiptLog;
import com.example.bloombackend.bottlemsg.entity.ReactionType;
import com.example.bloombackend.bottlemsg.repository.BottleMessageLogRepository;
import com.example.bloombackend.bottlemsg.repository.BottleMessageReactionRepositoryRepository;
import com.example.bloombackend.bottlemsg.repository.BottleMessageRepository;
import com.example.bloombackend.user.service.UserService;

@Service
public class BottleMessageService {
	private final BottleMessageRepository bottleMessageRepository;
	private final BottleMessageLogRepository bottleMessageLogRepository;
	private final UserService userService;
	private final BottleMessageReactionRepositoryRepository bottleMessageReactionRepository;

	@Autowired
	public BottleMessageService(BottleMessageRepository bottleMessageRepository,
		BottleMessageLogRepository botleMessageLogRepository, UserService userService,
		BottleMessageReactionRepositoryRepository bottleMessageReactionRepository) {
		this.bottleMessageRepository = bottleMessageRepository;
		this.bottleMessageLogRepository = botleMessageLogRepository;
		this.userService = userService;
		this.bottleMessageReactionRepository = bottleMessageReactionRepository;
	}

	@Transactional
	public CreateBottleMessageResponse createBottleMessage(Long userId, CreateBottleMessageRequest request) {
		return CreateBottleMessageResponse.of(bottleMessageRepository.save(
			BottleMessageEntity.builder()
				.content(request.content())
				.user(userService.findUserById(userId))
				.title(request.title())
				.postcardUrl(request.postCard())
				.build()
		).getId());
	}

	@Transactional
	public BottleMessageWithReactionResponse getRandomBottleMessage(Long userId) {
		BottleMessageEntity message = findRandomUnreceivedMessage(userId);
		BottleMessageReactionResponse reaction = getReactionCount(message.getId());
		createBottleMessageReceiptLog(userId, message);
		return new BottleMessageWithReactionResponse(message.toDto(), reaction);
	}

	private void createBottleMessageReceiptLog(Long userId, BottleMessageEntity message) {
		BottleMessageReceiptLog log = BottleMessageReceiptLog.builder()
			.recipient(userService.findUserById(userId))
			.message(message)
			.build();
		bottleMessageLogRepository.save(log);
	}

	private BottleMessageEntity findRandomUnreceivedMessage(Long userId) {
		List<BottleMessageEntity> unreceivedMessages = bottleMessageRepository.findUnreceivedMessagesByUserId(userId);

		if (unreceivedMessages.isEmpty()) {
			throw new NoSuchElementException("No unreceived messages found.");
		}

		return unreceivedMessages.get(new Random().nextInt(unreceivedMessages.size()));
	}

	private BottleMessageReactionResponse getReactionCount(Long messageId) {
		Map<ReactionType, Long> reactionsCountMap = bottleMessageReactionRepository.countReactionsByMessage(messageId);

		int likeCount = reactionsCountMap.getOrDefault(ReactionType.LIKE, 0L).intValue();
		int empathyCount = reactionsCountMap.getOrDefault(ReactionType.EMPATHY, 0L).intValue();
		int cheerCount = reactionsCountMap.getOrDefault(ReactionType.CHEER, 0L).intValue();

		return new BottleMessageReactionResponse(likeCount, empathyCount, cheerCount);
	}

	@Transactional(readOnly = true)
	public UserBottleMessagesResponse getBottleMessages(Long userId) {
		List<BottleMessageEntity> savedMessages = bottleMessageRepository.findSavedMessagesByUserId(userId);
		if (savedMessages.isEmpty()) {
			throw new NoSuchElementException("No unreceived messages found.");
		}
		return new UserBottleMessagesResponse(savedMessages.stream()
			.map(BottleMessageEntity::toDto)
			.collect(Collectors.toList()));
	}

	@Transactional(readOnly = true)
	public BottleMessageWithReactionResponse getBottleMessage(Long messageId) {
		BottleMessageEntity message = bottleMessageRepository.findById(messageId)
			.orElseThrow(() -> new NoSuchElementException("Message with ID " + messageId + " not found."));
		BottleMessageReactionResponse reaction = getReactionCount(message.getId());
		return new BottleMessageWithReactionResponse(message.toDto(), reaction);
	}

}
