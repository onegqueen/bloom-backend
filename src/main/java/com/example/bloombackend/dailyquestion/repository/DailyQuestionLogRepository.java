package com.example.bloombackend.dailyquestion.repository;

import com.example.bloombackend.dailyquestion.entity.DailyQuestionLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface DailyQuestionLogRepository extends JpaRepository<DailyQuestionLogEntity, Long> {
    Optional<DailyQuestionLogEntity> findFirstByOrderByCreatedAtDesc();

    Optional<DailyQuestionLogEntity> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime startOfDay, LocalDateTime endOfDay);
}