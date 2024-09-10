package com.example.bloombackend.bottlemsg.repository.querydsl;

import java.util.Map;

import com.example.bloombackend.bottlemsg.entity.ReactionType;

public interface BottleMessageReactionRepositoryCustom {
	Map<ReactionType, Long> countReactionsByMessage(Long messageId);
}
