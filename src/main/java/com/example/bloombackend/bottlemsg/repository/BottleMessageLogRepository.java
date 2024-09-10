package com.example.bloombackend.bottlemsg.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bloombackend.bottlemsg.entity.BottleMessageReceiptLog;
import com.example.bloombackend.user.entity.UserEntity;

public interface BottleMessageLogRepository extends JpaRepository<BottleMessageReceiptLog, Long> {
	Optional<BottleMessageReceiptLog> findByBottleMessageIdAndRecipient(Long bottleMessageId, UserEntity userEntity);
}
