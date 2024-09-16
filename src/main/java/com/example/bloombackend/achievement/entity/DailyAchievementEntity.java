package com.example.bloombackend.achievement.entity;

import com.example.bloombackend.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "daily_achievement")
public class DailyAchievementEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flower_id", nullable = false)
    private FlowerEntity flower;

    @Column(name = "achievement_level", nullable = false)
    private int achievementLevel = 0;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public DailyAchievementEntity(UserEntity user, FlowerEntity flower) {
        this.user = user;
        this.flower = flower;
    }

    public FlowerEntity getFlower() {
        return flower;
    }

    public int getAchievementLevel() {
        return achievementLevel;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int increaseAchievementLevel(int increaseBy) {
        if (achievementLevel + increaseBy >= 0 && achievementLevel + increaseBy <= 9) {
            achievementLevel += increaseBy;
        }
        return achievementLevel;
    }
}