package com.example.bloombackend.quest.entity;

import com.example.bloombackend.quest.controller.dto.response.QuestResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "quest")
public class QuestEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(name = "title")
    private String title;

    @Column(name = "max_count")
    private int maxCount;

    public QuestEntity(String iconUrl, String title, int maxCount) {
        this.iconUrl = iconUrl;
        this.title = title;
        this.maxCount = maxCount;
    }

    public QuestResponse toDto() {
        return new QuestResponse(id, iconUrl, title, maxCount);
    }
}