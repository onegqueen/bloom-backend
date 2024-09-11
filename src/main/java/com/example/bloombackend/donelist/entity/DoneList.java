package com.example.bloombackend.donelist.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.example.bloombackend.donelist.controller.dto.response.DoneItemDetailResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "done_list")
public class DoneList {
	@Id
	@GeneratedValue
	@Column(name = "done_list_id")
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "content")
	private String content;

	@Column(name = "icon_url")
	private String iconUrl;

	@Column(name = "photo_url")
	private String photoUrl;

	@Column(name = "user_id")
	private Long userId;

	@CreationTimestamp
	@Column(name = "create_at")
	private LocalDateTime createdAt;

	@Builder
	public DoneList(String content, String photoUrl, String title, String iconUrl, Long userId) {
		this.content = content;
		this.photoUrl = photoUrl;
		this.title = title;
		this.iconUrl = iconUrl;
		this.userId = userId;
	}

	public DoneItemDetailResponse toDto() {
		return DoneItemDetailResponse.builder()
			.content(content)
			.title(title)
			.iconUrl(iconUrl)
			.photoUrl(photoUrl)
			.build();
	}
}
