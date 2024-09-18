package com.example.bloombackend.quest.repository.querydsl;

import com.example.bloombackend.quest.entity.QuestEntity;
import com.example.bloombackend.user.entity.UserEntity;

import java.util.List;

public interface QuestRepositoryCustom {
    List<QuestEntity> findUnLoggedQuests(UserEntity user, List<Long> questIds);
}