package com.example.bloombackend.donelist.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bloombackend.donelist.controller.dto.request.CreateDoneItemRequest;
import com.example.bloombackend.donelist.controller.dto.response.DoneItemDetailResponse;
import com.example.bloombackend.donelist.controller.dto.response.DoneItemPhotoResponse;
import com.example.bloombackend.donelist.controller.dto.response.DoneItemResponse;
import com.example.bloombackend.donelist.entity.DoneList;
import com.example.bloombackend.donelist.entity.Photo;
import com.example.bloombackend.donelist.repository.DoneListRepository;
import com.example.bloombackend.donelist.repository.PhotoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DoneListService {
	private final DoneListRepository doneListRepository;
	private final PhotoRepository photoRepository;

	public DoneListService(DoneListRepository doneListRepository, PhotoRepository photoRepository) {
		this.doneListRepository = doneListRepository;
		this.photoRepository = photoRepository;
	}

	@Transactional
	public DoneItemDetailResponse createDoneItem(Long userId, CreateDoneItemRequest request) {
		DoneItemResponse content = createDoneItemContent(userId, request);
		List<DoneItemPhotoResponse> photos = createDoneItemPhoto(content.itemId(), request.photos());
		return new DoneItemDetailResponse(content, photos);
	}

	private DoneItemResponse createDoneItemContent(Long userId, CreateDoneItemRequest request) {
		return doneListRepository.save(
			DoneList.builder()
				.userId(userId)
				.title(request.title())
				.iconUrl(request.iconUrl())
				.content(request.content()).build()
		).toDto();
	}

	private List<DoneItemPhotoResponse> createDoneItemPhoto(Long doneListId, List<String> photoFiles) {
		List<Photo> photos = photoFiles.stream()
			.map(photoFile -> new Photo(photoFile, doneListId))
			.collect(Collectors.toList());
		return getPhotoResponses(photoRepository.saveAll(photos));
	}

	private List<DoneItemPhotoResponse> getPhotoResponses(List<Photo> photos) {
		return photos.stream()
			.map(photo -> photo.toDto())
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public DoneItemDetailResponse getDoneItem(Long userId, Long itemId) {
		DoneItemResponse doneitem = getDoneListEntity(itemId).toDto();
		List<DoneItemPhotoResponse> photos = getPhotosEntity(itemId).stream()
			.map(Photo::toDto)
			.collect(Collectors.toList());

		return new DoneItemDetailResponse(doneitem, photos);
	}

	private DoneList getDoneListEntity(Long itemId) {
		return doneListRepository.findById(itemId)
			.orElseThrow(() -> new EntityNotFoundException("User not found: " + itemId));
	}

	private List<Photo> getPhotosEntity(Long itemId) {
		return photoRepository.findByDoneListId(itemId);
	}

	private Map<String, LocalDateTime> getStartAndEndOfDay(LocalDate date) {
		Map<String, LocalDateTime> startAndEndOfDay = new HashMap<>();
		LocalDateTime start = LocalDateTime.of(date, LocalTime.of(0, 0, 0));
		LocalDateTime end = LocalDateTime.of(date, LocalTime.of(23, 59, 59));

		startAndEndOfDay.put("start", start);
		startAndEndOfDay.put("end", end);

		return startAndEndOfDay;
	}

}
