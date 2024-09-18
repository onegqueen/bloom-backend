package com.example.bloombackend.quest.repository.querydsl;

public interface UserQuestLogRepositoryCustom {
    void completeQuest(Long userId, Long questId);
}