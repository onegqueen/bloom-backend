package com.example.bloombackend.dailyquestion.entity;

import com.example.bloombackend.dailyquestion.controller.response.DailyQuestionResponse;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "daily_question")
public class DailyQuestionEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    public DailyQuestionEntity(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public DailyQuestionResponse toDto() {
        return new DailyQuestionResponse(content);
    }
}