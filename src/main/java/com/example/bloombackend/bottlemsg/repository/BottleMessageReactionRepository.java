package com.example.bloombackend.bottlemsg.repository;

import com.example.bloombackend.bottlemsg.entity.ReactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bloombackend.bottlemsg.entity.BottleMessageReaction;
import com.example.bloombackend.bottlemsg.repository.querydsl.BottleMessageReactionRepositoryCustom;

import java.util.Optional;

public interface BottleMessageReactionRepository
	extends JpaRepository<BottleMessageReaction, Long>, BottleMessageReactionRepositoryCustom {
	Optional<BottleMessageReaction> findByReactorId(Long UserId);
	Void deleteByMessageIdAndUserIdAndReactionType(Long MessageId, Long UserId, ReactionType ReactionType);
}
