package com.example.bloombackend.bottlemsg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bloombackend.bottlemsg.entity.BottleMessageEntity;

@Repository
public interface BottleMessageRepository extends JpaRepository<BottleMessageEntity, Long> {
}
