package com.example.bloombackend.bottlemsg.repository.querydsl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.bloombackend.bottlemsg.entity.BottleMessageEntity;
import com.example.bloombackend.bottlemsg.entity.QBottleMessageEntity;
import com.example.bloombackend.bottlemsg.entity.QBottleMessageReceiptLog;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class BottleMessageRepositoryImpl implements BottleMessageRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	public BottleMessageRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public List<BottleMessageEntity> findUnreceivedMessagesByUserId(Long userId) {
		QBottleMessageEntity bottleMessage = QBottleMessageEntity.bottleMessageEntity;
		QBottleMessageReceiptLog receiptLog = QBottleMessageReceiptLog.bottleMessageReceiptLog;

		return queryFactory
			.selectFrom(bottleMessage)
			.where(bottleMessage.id.notIn(
				queryFactory.select(receiptLog.message.id)
					.from(receiptLog)
					.where(receiptLog.recipient.id.eq(userId))
			))
			.fetch();
	}

	@Override
	public List<BottleMessageEntity> findSavedMessagesByUserId(Long userId) {
		QBottleMessageEntity bottleMessage = QBottleMessageEntity.bottleMessageEntity;
		QBottleMessageReceiptLog receiptLog = QBottleMessageReceiptLog.bottleMessageReceiptLog;

		return queryFactory
			.selectFrom(bottleMessage)
			.where(bottleMessage.id.in(
				queryFactory.select(receiptLog.message.id)
					.from(receiptLog)
					.where(receiptLog.recipient.id.eq(userId)
						.and(receiptLog.isSaved.eq(true)))
			))
			.fetch();
	}
}
