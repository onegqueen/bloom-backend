package com.example.bloombackend.donelist.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.example.bloombackend.donelist.controller.dto.response.DoneItemResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "done_list_id")
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "content")
	private String content;

	@Column(name = "icon_url")
	private String iconUrl;

	@Column(name = "user_id")
	private Long userId;

	@CreationTimestamp
	@Column(name = "create_at")
	private LocalDateTime createdAt;

	@Column(name = "done_date")
	private LocalDate doneDate;

	@Builder
	public DoneList(String content, String photoUrl, String title, String iconUrl, Long userId,LocalDate doneDate) {
		this.content = content;
		this.title = title;
		this.iconUrl = iconUrl;
		this.userId = userId;
		this.doneDate = doneDate;
	}

	public DoneItemResponse toDto() {
		return DoneItemResponse.builder()
			.itemId(id)
			.content(content)
			.title(title)
			.iconUrl(iconUrl)
			.build();
	}

	public void updateContent(String content) {
		this.content = content;
	}

	public void updateTitle(String title) {
		this.title = title;
	}

}
