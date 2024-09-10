package com.example.bloombackend.bottlemsg.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.example.bloombackend.bottlemsg.controller.dto.response.BottleMessageResponse;
import com.example.bloombackend.user.entity.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity sender;

	@Column(name = "title")
	private String title;

	@Column(name = "content", length = 500)
	private String content;

	@Column(name = "postcard_url")
	private String postcardUrl;

	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Builder
	public BottleMessageEntity(UserEntity user, String content, String title, String postcardUrl) {
		this.sender = user;
		this.content = content;
		this.title = title;
		this.postcardUrl = postcardUrl;
	}

	public Long getId() {
		return id;
	}

	public BottleMessageResponse toDto() {
		return BottleMessageResponse.builder()
			.messageId(id)
			.content(content)
			.title(title)
			.postCardUrl(postcardUrl)
			.build();
	}
}
