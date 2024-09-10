package com.example.bloombackend.bottlemsg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bloombackend.bottlemsg.entity.BottleMessageReceiptLog;

public interface BottleMessageLogRepository extends JpaRepository<BottleMessageReceiptLog, Long> {
}
