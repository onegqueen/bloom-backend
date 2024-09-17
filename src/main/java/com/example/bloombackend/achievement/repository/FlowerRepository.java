package com.example.bloombackend.achievement.repository;

import com.example.bloombackend.achievement.entity.FlowerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowerRepository extends JpaRepository<FlowerEntity, Long> {
}