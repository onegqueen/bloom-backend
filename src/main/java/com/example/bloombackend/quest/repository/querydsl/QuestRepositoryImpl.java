package com.example.bloombackend.quest.repository.querydsl;

import com.example.bloombackend.quest.entity.QQuestEntity;
import com.example.bloombackend.quest.entity.QUserQuestLogEntity;
import com.example.bloombackend.quest.entity.QuestEntity;
import com.example.bloombackend.user.entity.UserEntity;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class QuestRepositoryImpl implements QuestRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public QuestRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<QuestEntity> findUnLoggedQuests(UserEntity user, List<Long> questIds) {
        QUserQuestLogEntity qLog = QUserQuestLogEntity.userQuestLogEntity;
        QQuestEntity qQuest = QQuestEntity.questEntity;

        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        JPQLQuery<Long> loggedQuestIds = JPAExpressions
                .select(qLog.quest.id)
                .from(qLog)
                .where(qLog.user.eq(user)
                        .and(qLog.selectedDate.between(startOfDay, endOfDay)));

        return queryFactory
                .selectFrom(qQuest)
                .where(qQuest.id.in(questIds)
                        .and(qQuest.id.notIn(loggedQuestIds)))
                .fetch();
    }
}