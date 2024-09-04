package com.example.bloombackend.user.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider",nullable = false)
    private OAuthProvider provider;

    @Column(name="email")
    private String email;

    @Column(name="name",nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name="gender")
    private Gender gender;

    @Column(name="age")
    private int age;

    @CreationTimestamp
    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public UserEntity(OAuthProvider provider, String name) {
        this.provider = provider;
        this.name = name;
    }

    public void updateUserSurveyInfo(String newName, int age, Gender gender, String email) {
        this.name = newName;
        this.age = age;
        this.gender = gender;
        this.email = email;
    }
}
