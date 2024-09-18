package com.example.bloombackend.quest.repository;

import com.example.bloombackend.quest.entity.QuestEntity;
import com.example.bloombackend.quest.repository.querydsl.QuestRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestRepository extends JpaRepository<QuestEntity, Long>, QuestRepositoryCustom {
}