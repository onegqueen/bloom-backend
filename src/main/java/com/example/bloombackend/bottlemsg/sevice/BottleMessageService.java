package com.example.bloombackend.bottlemsg.sevice;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bloombackend.bottlemsg.controller.dto.request.CreateBottleMessageReactionRequest;
import com.example.bloombackend.bottlemsg.controller.dto.request.CreateBottleMessageRequest;
import com.example.bloombackend.bottlemsg.controller.dto.response.BottleMessageReactionResponse;
import com.example.bloombackend.bottlemsg.controller.dto.response.BottleMessageWithReactionResponse;
import com.example.bloombackend.bottlemsg.controller.dto.response.CreateBottleMessageResponse;
import com.example.bloombackend.bottlemsg.controller.dto.response.UserBottleMessagesResponse;
import com.example.bloombackend.bottlemsg.entity.BottleMessageEntity;
import com.example.bloombackend.bottlemsg.entity.BottleMessageReaction;
import com.example.bloombackend.bottlemsg.entity.BottleMessageReceiptLog;
import com.example.bloombackend.bottlemsg.entity.ReactionType;
import com.example.bloombackend.bottlemsg.repository.BottleMessageLogRepository;
import com.example.bloombackend.bottlemsg.repository.BottleMessageReactionRepositoryRepository;
import com.example.bloombackend.bottlemsg.repository.BottleMessageRepository;
import com.example.bloombackend.user.entity.UserEntity;
import com.example.bloombackend.user.service.UserService;

@Service
public class BottleMessageService {
	private final BottleMessageRepository bottleMessageRepository;
	private final BottleMessageLogRepository bottleMessageLogRepository;
	private final UserService userService;
	private final BottleMessageReactionRepositoryRepository bottleMessageReactionRepository;

	@Autowired
	public BottleMessageService(BottleMessageRepository bottleMessageRepository,
		BottleMessageLogRepository bottleMessageLogRepository, UserService userService,
		BottleMessageReactionRepositoryRepository bottleMessageReactionRepository) {
		this.bottleMessageRepository = bottleMessageRepository;
		this.bottleMessageLogRepository = bottleMessageLogRepository;
		this.userService = userService;
		this.bottleMessageReactionRepository = bottleMessageReactionRepository;
	}

	@Transactional
	public CreateBottleMessageResponse createBottleMessage(Long userId, CreateBottleMessageRequest request) {
		return CreateBottleMessageResponse.of(bottleMessageRepository.save(BottleMessageEntity.builder()
			.content(request.content())
			.user(userService.findUserById(userId))
			.title(request.title())
			.postcardUrl(request.postCard())
			.build()).getId());
	}

	@Transactional
	public BottleMessageWithReactionResponse getRandomBottleMessage(Long userId) {
		BottleMessageEntity message = findRandomUnreceivedMessage(userId);
		BottleMessageReactionResponse reaction = getReactionCount(message.getId());
		createBottleMessageReceiptLog(userId, message);
		return new BottleMessageWithReactionResponse(message.toDto(), reaction);
	}

	private void createBottleMessageReceiptLog(Long userId, BottleMessageEntity message) {
		bottleMessageLogRepository.save(
			BottleMessageReceiptLog.builder().recipient(userService.findUserById(userId)).message(message).build());
	}

	private BottleMessageEntity findRandomUnreceivedMessage(Long userId) {
		List<BottleMessageEntity> unreceivedMessages = bottleMessageRepository.findUnreceivedMessagesByUserId(userId);
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
	public UserBottleMessagesResponse getUserBottleMessages(Long userId) {
		List<BottleMessageEntity> savedMessages = bottleMessageRepository.findSavedMessagesByUserId(userId);
		return new UserBottleMessagesResponse(
			savedMessages.stream().map(BottleMessageEntity::toDto).collect(Collectors.toList()));
	}

	@Transactional(readOnly = true)
	public BottleMessageWithReactionResponse getBottleMessage(Long messageId) {
		BottleMessageEntity message = getBottleMessageEntity(messageId);
		BottleMessageReactionResponse reaction = getReactionCount(message.getId());
		return new BottleMessageWithReactionResponse(message.toDto(), reaction);
	}

	@Transactional
	public BottleMessageReactionResponse updateBottleMessageReaction(Long messageId,
		CreateBottleMessageReactionRequest request) {
		ReactionType reactionType = ReactionType.valueOf(request.reaction());
		BottleMessageEntity message = getBottleMessageEntity(messageId);
		return getReactionCount(bottleMessageReactionRepository.save(
			BottleMessageReaction.builder().message(message).reactionType(reactionType).build()).getMessage().getId());
	}

	@Transactional
	public UserBottleMessagesResponse deleteBottleMessage(Long messageId, Long userId) {
		UserEntity recipient = userService.findUserById(userId);
		Optional<BottleMessageReceiptLog> message = bottleMessageLogRepository.findByBottleMessageIdAndRecipient(
			messageId, recipient);

		if (message.isPresent()) {
			message.get().delete();
		}

		return getUserBottleMessages(userId);
	}

	private BottleMessageEntity getBottleMessageEntity(Long messageId) {
		return bottleMessageRepository.findById(messageId)
			.orElseThrow(() -> new NoSuchElementException("Message with ID " + messageId + " not found."));
	}
}
