package com.example.bloombackend.quest.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;

import static com.example.bloombackend.quest.entity.QUserQuestLogEntity.userQuestLogEntity;

public class UserQuestLogRepositoryImpl implements UserQuestLogRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public UserQuestLogRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public void completeQuest(Long userId, Long questId) {
        queryFactory.update(userQuestLogEntity)
                .set(userQuestLogEntity.isDone, true)
                .where(userQuestLogEntity.user.id.eq(userId)
                        .and(userQuestLogEntity.quest.id.eq(questId)))
                .execute();
    }
}