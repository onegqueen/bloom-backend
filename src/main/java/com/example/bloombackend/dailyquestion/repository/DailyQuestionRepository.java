package com.example.bloombackend.dailyquestion.repository;

import com.example.bloombackend.dailyquestion.entity.DailyQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyQuestionRepository extends JpaRepository<DailyQuestionEntity, Long> {
}