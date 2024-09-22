package com.example.bloombackend.bottlemsg.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.example.bloombackend.bottlemsg.controller.dto.response.BottleMessageResponse;
import com.example.bloombackend.user.entity.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "bottle_message")
public class BottleMessageEntity {
	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "message_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity sender;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "content", length = 500, nullable = false)
	private String content;

	@Column(name = "postcard_url", nullable = false)
	private String postcardUrl;

	@Enumerated(EnumType.STRING)
	@Column(name = "negativity")
	private Nagativity nagativity;

	@Getter
	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Builder
	public BottleMessageEntity(UserEntity user, String content, String title, String postcardUrl, Nagativity nagativity,
		LocalDateTime sentAt) {
		this.sender = user;
		this.content = content;
		this.title = title;
		this.postcardUrl = postcardUrl;
		this.nagativity = nagativity;
	}

	public BottleMessageResponse toDto() {
		return BottleMessageResponse.builder()
			.messageId(id)
			.content(content)
			.title(title)
			.postCardUrl(postcardUrl)
			.negativity(nagativity.name())
			.build();
	}
}
