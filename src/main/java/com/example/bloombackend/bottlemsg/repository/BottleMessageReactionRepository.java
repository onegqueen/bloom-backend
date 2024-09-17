package com.example.bloombackend.bottlemsg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bloombackend.bottlemsg.entity.BottleMessageReaction;
import com.example.bloombackend.bottlemsg.repository.querydsl.BottleMessageReactionRepositoryCustom;

import java.util.Optional;

public interface BottleMessageReactionRepository
	extends JpaRepository<BottleMessageReaction, Long>, BottleMessageReactionRepositoryCustom {
	Optional<BottleMessageReaction> findByReactorId(Long UserId);
}
