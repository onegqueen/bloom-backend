package com.example.bloombackend.quest.repository;

import com.example.bloombackend.quest.entity.UserQuestLogEntity;
import com.example.bloombackend.quest.repository.querydsl.UserQuestLogRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UserQuestLogRepository extends JpaRepository<UserQuestLogEntity, Long>, UserQuestLogRepositoryCustom {
    List<UserQuestLogEntity> findAllByUserIdAndSelectedDateBetween(Long userId, LocalDateTime startOfDay, LocalDateTime endOfDay);

    void deleteByUserIdAndQuestId(Long userId, Long questId);
}