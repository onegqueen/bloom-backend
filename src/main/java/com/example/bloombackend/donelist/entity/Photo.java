package com.example.bloombackend.donelist.entity;

import com.example.bloombackend.donelist.controller.dto.response.DoneItemPhotoResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "done_list_photo")
public class Photo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "photo_id")
	@Getter
	private Long id;

	@Column(name = "photo_url")
	private String photoUrl;

	@JoinColumn(name = "done_list_id")
	private Long doneListId;

	@Builder
	public Photo(String photoUrl, Long doneListId) {
		this.photoUrl = photoUrl;
		this.doneListId = doneListId;
	}

	public DoneItemPhotoResponse toDto() {
		return new DoneItemPhotoResponse(id, photoUrl);
	}
}
