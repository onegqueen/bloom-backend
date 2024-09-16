package com.example.bloombackend.achievement.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "flower")
public class FlowerEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "icon_url", nullable = false)
    private String iconUrl;

    @Column(name = "description")
    private String description;

    public FlowerEntity(String iconUrl, String description) {
        this.iconUrl = iconUrl;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getIconUrl() {
        return iconUrl;
    }
}