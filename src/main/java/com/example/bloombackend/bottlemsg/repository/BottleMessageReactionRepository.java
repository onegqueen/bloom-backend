package com.example.bloombackend.bottlemsg.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bloombackend.bottlemsg.entity.BottleMessageReaction;
import com.example.bloombackend.bottlemsg.entity.ReactionType;
import com.example.bloombackend.bottlemsg.repository.querydsl.BottleMessageReactionRepositoryCustom;
import com.example.bloombackend.user.entity.UserEntity;

public interface BottleMessageReactionRepository
	extends JpaRepository<BottleMessageReaction, Long>, BottleMessageReactionRepositoryCustom {
	Optional<BottleMessageReaction> findByReactor(UserEntity reactor);

	Void deleteByMessageIdAndReactorAndReactionType(Long messageId, UserEntity reactor, ReactionType reactionType);
}
