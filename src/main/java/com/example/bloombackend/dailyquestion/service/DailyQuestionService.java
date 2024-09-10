package com.example.bloombackend.dailyquestion.service;

import com.example.bloombackend.dailyquestion.controller.request.DailyQuestionAnswerRequest;
import com.example.bloombackend.dailyquestion.controller.response.DailyQuestionAnswerResponse;
import com.example.bloombackend.dailyquestion.controller.response.DailyQuestionResponse;
import com.example.bloombackend.dailyquestion.entity.DailyQuestionEntity;
import com.example.bloombackend.dailyquestion.entity.DailyQuestionLogEntity;
import com.example.bloombackend.dailyquestion.repository.DailyQuestionLogRepository;
import com.example.bloombackend.dailyquestion.repository.DailyQuestionRepository;
import com.example.bloombackend.user.entity.UserEntity;
import com.example.bloombackend.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DailyQuestionService {
    private final DailyQuestionRepository dailyQuestionRepository;
    private final DailyQuestionLogRepository dailyQuestionLogRepository;
    private final UserRepository userRepository;

    public DailyQuestionService(DailyQuestionRepository dailyQuestionRepository,
                                DailyQuestionLogRepository dailyQuestionLogRepository,
                                UserRepository userRepository) {
        this.dailyQuestionRepository = dailyQuestionRepository;
        this.dailyQuestionLogRepository = dailyQuestionLogRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public DailyQuestionResponse getDailyQuestion(Long userId) {
        Optional<DailyQuestionLogEntity> dailyQuestionLog = dailyQuestionLogRepository.findFirstByOrderByCreatedAtDesc();
        if (isOutdatedOrEmpty(dailyQuestionLog)) {
            DailyQuestionEntity newDailyQuestion = registerNewDailyQuestion(userId, getNextQuestionId(dailyQuestionLog));
            return newDailyQuestion.toDto();
        }
        return dailyQuestionLog.get().getQuestion().toDto();
    }

    private boolean isOutdatedOrEmpty(Optional<DailyQuestionLogEntity> dailyQuestionLog) {
        return dailyQuestionLog.isEmpty() || dailyQuestionLog.get().getCreatedAt().isBefore(getStartOfDay(LocalDate.now()));
    }

    private Long getNextQuestionId(Optional<DailyQuestionLogEntity> dailyQuestionLog) {
        return dailyQuestionLog.map(DailyQuestionLogEntity::getQuestion).map(DailyQuestionEntity::getId).orElse(0L) + 1;
    }

    private DailyQuestionEntity registerNewDailyQuestion(Long userId, Long questionId) {
        DailyQuestionEntity newDailyQuestion = getQuestionEntity(questionId);
        UserEntity user = getUserEntity(userId);
        dailyQuestionLogRepository.save(new DailyQuestionLogEntity(user, newDailyQuestion));
        return newDailyQuestion;
    }

    @Transactional(readOnly = true)
    public DailyQuestionAnswerResponse getDailyQuestionAnswer(Long userId, String date) {
        DailyQuestionLogEntity dailyQuestionLog = getDailyQuestionLogEntity(userId, LocalDate.parse(date));
        return dailyQuestionLog.toDto();
    }

    @Transactional
    public void updateDailyQuestionAnswer(Long userId, DailyQuestionAnswerRequest request) {
        DailyQuestionLogEntity dailyQuestionLogForToday = getDailyQuestionLogEntity(userId, LocalDate.now());
        dailyQuestionLogForToday.updateAnswer(request.answer());
        dailyQuestionLogRepository.save(dailyQuestionLogForToday);
    }

    private DailyQuestionLogEntity getDailyQuestionLogEntity(final Long userId, final LocalDate date) {
        return dailyQuestionLogRepository.findByUserIdAndCreatedAtBetween(userId, getStartOfDay(date), getEndOfDay(date))
                .orElseThrow(() -> new EntityNotFoundException("answer not found: " + date));
    }

    private UserEntity getUserEntity(final Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
    }

    private DailyQuestionEntity getQuestionEntity(final Long questionId) {
        return dailyQuestionRepository.findById(questionId).orElseThrow(() -> new EntityNotFoundException("Daily question not found: " + questionId));
    }

    private static LocalDateTime getStartOfDay(final LocalDate localDate) {
        return localDate.atStartOfDay();
    }

    private static LocalDateTime getEndOfDay(final LocalDate localDate) {
        return localDate.atTime(23, 59, 59);
    }
}