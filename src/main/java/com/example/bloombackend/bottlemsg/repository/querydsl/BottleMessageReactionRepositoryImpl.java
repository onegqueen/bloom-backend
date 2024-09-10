package com.example.bloombackend.bottlemsg.repository.querydsl;

import java.util.Map;

import com.example.bloombackend.bottlemsg.entity.QBottleMessageReaction;
import com.example.bloombackend.bottlemsg.entity.ReactionType;
import com.querydsl.core.group.GroupBy;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class BottleMessageReactionRepositoryImpl implements BottleMessageReactionRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	public BottleMessageReactionRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public Map<ReactionType, Long> countReactionsByMessage(Long messageId) {
		QBottleMessageReaction bottleMessageReaction = QBottleMessageReaction.bottleMessageReaction;

		return queryFactory
			.from(bottleMessageReaction)
			.where(bottleMessageReaction.message.id.eq(messageId))  // message.id로 수정
			.groupBy(bottleMessageReaction.reactionType)
			.transform(GroupBy.groupBy(bottleMessageReaction.reactionType).as(bottleMessageReaction.count()));
	}
}
