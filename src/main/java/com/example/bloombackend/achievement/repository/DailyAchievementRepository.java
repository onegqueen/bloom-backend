package com.example.bloombackend.achievement.repository;

import com.example.bloombackend.achievement.entity.DailyAchievementEntity;
import com.example.bloombackend.achievement.repository.querydsl.DailyAchievementRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DailyAchievementRepository extends JpaRepository<DailyAchievementEntity, Long>, DailyAchievementRepositoryCustom {
    List<DailyAchievementEntity> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);

    Optional<DailyAchievementEntity> findFirstByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);

    boolean existsByUserIdAndCreatedAtBetween(Long userId, LocalDateTime startOfToday, LocalDateTime endOfToday);
}