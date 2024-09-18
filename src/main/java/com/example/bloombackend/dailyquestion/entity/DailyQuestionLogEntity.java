package com.example.bloombackend.dailyquestion.entity;

import com.example.bloombackend.dailyquestion.controller.response.DailyQuestionAnswerResponse;
import com.example.bloombackend.dailyquestion.controller.response.DailyQuestionResponse;
import com.example.bloombackend.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "daily_question_log")
public class DailyQuestionLogEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private DailyQuestionEntity dailyQuestion;

    @Column(name = "answer")
    private String answer;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public DailyQuestionLogEntity(UserEntity user, DailyQuestionEntity dailyQuestion) {
        this.user = user;
        this.dailyQuestion = dailyQuestion;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public DailyQuestionEntity getQuestion() {
        return dailyQuestion;
    }

    public void updateAnswer(String answer) {
        this.answer = answer;
    }

    public DailyQuestionAnswerResponse toDto() {
        DailyQuestionResponse question = dailyQuestion.toDto();
        return new DailyQuestionAnswerResponse(question.question(), answer);
    }
}