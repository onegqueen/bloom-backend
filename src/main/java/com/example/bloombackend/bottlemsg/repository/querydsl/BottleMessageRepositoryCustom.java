package com.example.bloombackend.bottlemsg.repository.querydsl;

import java.util.List;

import com.example.bloombackend.bottlemsg.entity.BottleMessageEntity;

public interface BottleMessageRepositoryCustom {
	List<BottleMessageEntity> findUnreceivedMessagesByUserId(Long userId);
}
