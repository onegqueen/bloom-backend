package com.example.bloombackend.bottlemsg.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.example.bloombackend.user.entity.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "bottle-message")
public class BottleMessageEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "message_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity senderId;

	@Column(length = 500)
	private String content;

	@Column(name = "postcard_url")
	private String postcardUrl;

	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Builder
	public BottleMessageEntity(UserEntity user, String content, String postcardUrl) {
		this.senderId = user;
		this.content = content;
		this.postcardUrl = postcardUrl;
	}

}
