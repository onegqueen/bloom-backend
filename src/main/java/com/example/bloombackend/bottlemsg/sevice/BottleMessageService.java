package com.example.bloombackend.bottlemsg.sevice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import com.example.bloombackend.bottlemsg.controller.dto.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bloombackend.bottlemsg.controller.dto.request.CreateBottleMessageReactionRequest;
import com.example.bloombackend.bottlemsg.controller.dto.request.CreateBottleMessageRequest;
import com.example.bloombackend.bottlemsg.entity.BottleMessageEntity;
import com.example.bloombackend.bottlemsg.entity.BottleMessageReaction;
import com.example.bloombackend.bottlemsg.entity.BottleMessageReceiptLog;
import com.example.bloombackend.bottlemsg.entity.ReactionType;
import com.example.bloombackend.bottlemsg.repository.BottleMessageLogRepository;
import com.example.bloombackend.bottlemsg.repository.BottleMessageReactionRepository;
import com.example.bloombackend.bottlemsg.repository.BottleMessageRepository;
import com.example.bloombackend.user.entity.UserEntity;
import com.example.bloombackend.user.service.UserService;

import static java.util.stream.Nodes.collect;

@Service
public class BottleMessageService {
    private final BottleMessageRepository bottleMessageRepository;
    private final BottleMessageLogRepository bottleMessageLogRepository;
    private final UserService userService;
    private final BottleMessageReactionRepository bottleMessageReactionRepository;

    @Autowired
    public BottleMessageService(BottleMessageRepository bottleMessageRepository,
                                BottleMessageLogRepository bottleMessageLogRepository, UserService userService,
                                BottleMessageReactionRepository bottleMessageReactionRepository) {
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
        createBottleMessageReceiptLog(userId, message);
        return getBottleMessage(message.getId(), userId);
    }

    private boolean isReacted(Long userId) {
        return bottleMessageReactionRepository.findByReactorId(userId).isPresent();
    }

    private void createBottleMessageReceiptLog(Long userId, BottleMessageEntity message) {
        bottleMessageLogRepository.save(
                BottleMessageReceiptLog.builder().recipient(userService.findUserById(userId)).message(message).build());
    }

    private BottleMessageEntity findRandomUnreceivedMessage(Long userId) {
        List<BottleMessageEntity> unreceivedMessages = bottleMessageRepository.findUnreceivedMessagesByUserId(userId);
        return unreceivedMessages.get(new Random().nextInt(unreceivedMessages.size()));
    }

    private BottleMessageReactionResponse getReactionCount(Long messageId, boolean isReacted) {
        Map<ReactionType, Long> reactionsCountMap = bottleMessageReactionRepository.countReactionsByMessage(messageId);

        int likeCount = reactionsCountMap.getOrDefault(ReactionType.LIKE, 0L).intValue();
        int empathyCount = reactionsCountMap.getOrDefault(ReactionType.EMPATHY, 0L).intValue();
        int cheerCount = reactionsCountMap.getOrDefault(ReactionType.CHEER, 0L).intValue();

        return new BottleMessageReactionResponse(isReacted, likeCount, empathyCount, cheerCount);
    }

    @Transactional(readOnly = true)
    public UserBottleMessagesResponse getUserBottleMessages(Long userId) {
        List<BottleMessageEntity> savedMessages = bottleMessageRepository.findSavedMessagesByUserId(userId);
        return getBottleMessages(savedMessages,userId);
    }


    @Transactional(readOnly = true)
    public BottleMessageWithReactionResponse getBottleMessage(Long messageId, Long userId) {
        BottleMessageEntity message = getBottleMessageEntity(messageId);
        BottleMessageReactionResponse reaction = getReactionCount(messageId, isReacted(userId));
        return new BottleMessageWithReactionResponse(message.toDto(), reaction);
    }

    private BottleMessageLogResponse getDateLog(Long messageId, Long userId) {
        BottleMessageReceiptLog log = bottleMessageLogRepository.findByMessageIdAndRecipient(messageId, userId).get();
        BottleMessageEntity message = getBottleMessageEntity(messageId);
        return new BottleMessageLogResponse(localDateToString(log.getReceivedAt(), "yyyy-MM-dd HH:mm:ss"),
                localDateToString(message.getCreatedAt(), "yyyy-MM-dd HH:mm:ss"));
    }

    private String localDateToString(LocalDateTime date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return date.format(formatter);
    }

    @Transactional
    public BottleMessageReactionResponse updateBottleMessageReaction(Long messageId, Long userId,
                                                                     CreateBottleMessageReactionRequest request) {
        UserEntity reactor = userService.findUserById(userId);
        bottleMessageReactionRepository.save(
                BottleMessageReaction.builder()
                        .reactor(reactor)
                        .message(getBottleMessageEntity(messageId))
                        .reactionType(ReactionType.valueOf(request.reaction()))
                        .build());
        return getReactionCount(messageId, isReacted(userId));
    }

    @Transactional
    public UserBottleMessagesResponse deleteBottleMessage(Long userId, Long messageId) {
        Optional<BottleMessageReceiptLog> message = bottleMessageLogRepository.findByMessageIdAndRecipient(
                messageId, userId);

        if (message.isPresent()) {
            message.get().delete();
        }

        return getUserBottleMessages(userId);
    }

    private BottleMessageEntity getBottleMessageEntity(Long messageId) {
        return bottleMessageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message with ID " + messageId + " not found."));
    }

    @Transactional(readOnly = true)
    public UserBottleMessagesResponse getSentBottleMessages(Long userId) {
        List<BottleMessageEntity> sentMessages = bottleMessageRepository.findBySenderId(userId);
        return getBottleMessages(sentMessages,userId);
    }

    private UserBottleMessagesResponse getBottleMessages(List<BottleMessageEntity> bottleMessageEntities,Long userId) {
        return new UserBottleMessagesResponse(bottleMessageEntities.stream()
                .map(savedMessage -> {
                            BottleMessageLogResponse log = getDateLog(savedMessage.getId(), userId);
                            return BottleMessageWithDateLogResponse.of(log, savedMessage.toDto());
                        }
                )
                .collect(Collectors.toList()));
    }
}
