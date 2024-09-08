package com.example.bloombackend.quest.entity;

import com.example.bloombackend.quest.controller.dto.response.QuestDto;
import com.example.bloombackend.quest.controller.dto.response.UserQuestLogDto;
import com.example.bloombackend.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_quest_log")
public class UserQuestLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_id", nullable = false)
    private QuestEntity quest;

    @Column(name = "is_done", nullable = false)
    private boolean isDone = false;

    @Column(name = "selected_date", nullable = false)
    private LocalDateTime selectedDate = LocalDateTime.now();

    public UserQuestLogEntity(UserEntity user, QuestEntity quest) {
        this.user = user;
        this.quest = quest;
    }

    public UserQuestLogDto toDto() {
        QuestDto questDto = quest.toDto();
        return new UserQuestLogDto(questDto.id(), questDto.iconUrl(), questDto.title(), questDto.maxCount(), isDone);
    }
}