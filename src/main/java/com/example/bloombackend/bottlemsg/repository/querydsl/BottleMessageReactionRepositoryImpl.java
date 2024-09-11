package com.example.bloombackend.bottlemsg.repository.querydsl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.bloombackend.bottlemsg.entity.QBottleMessageReaction;
import com.example.bloombackend.bottlemsg.entity.ReactionType;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class BottleMessageReactionRepositoryImpl implements BottleMessageReactionRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	public BottleMessageReactionRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public Map<ReactionType, Long> countReactionsByMessage(Long messageId) {
		QBottleMessageReaction bottleMessageReaction = QBottleMessageReaction.bottleMessageReaction;

		List<Tuple> result = queryFactory
			.select(bottleMessageReaction.reactionType, bottleMessageReaction.id.count())
			.from(bottleMessageReaction)
			.where(bottleMessageReaction.message.id.eq(messageId))
			.groupBy(bottleMessageReaction.reactionType)
			.fetch();

		// 결과를 map으로 변환
		return result.stream()
			.collect(Collectors.toMap(
				tuple -> tuple.get(bottleMessageReaction.reactionType),
				tuple -> tuple.get(bottleMessageReaction.id.count())
			));
	}
}

