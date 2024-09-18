package com.example.bloombackend.donelist.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.bloombackend.donelist.controller.dto.request.CreateDoneItemRequest;
import com.example.bloombackend.donelist.controller.dto.request.UpdateDoneItemRequest;
import com.example.bloombackend.donelist.controller.dto.response.DoneItemDetailResponse;
import com.example.bloombackend.donelist.controller.dto.response.DoneItemPhotoResponse;
import com.example.bloombackend.donelist.controller.dto.response.DoneItemResponse;
import com.example.bloombackend.donelist.controller.dto.response.DoneListResponse;
import com.example.bloombackend.donelist.entity.DoneList;
import com.example.bloombackend.donelist.entity.Photo;
import com.example.bloombackend.donelist.repository.DoneListRepository;
import com.example.bloombackend.donelist.repository.PhotoRepository;
import com.example.bloombackend.global.S3.S3Uploader;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DoneListService {
	private final DoneListRepository doneListRepository;
	private final PhotoRepository photoRepository;
	private final S3Uploader s3Uploader;

	public DoneListService(DoneListRepository doneListRepository, PhotoRepository photoRepository,
		S3Uploader s3Uploader) {
		this.doneListRepository = doneListRepository;
		this.photoRepository = photoRepository;
		this.s3Uploader = s3Uploader;
	}

	@Transactional
	public DoneItemDetailResponse createDoneItem(Long userId, CreateDoneItemRequest request,
		List<MultipartFile> photoFiles) {
		DoneItemResponse content = createDoneItemContent(userId, request);
		List<DoneItemPhotoResponse> photos = getPhotoResponseIfExist(photoFiles, content.itemId());
		return new DoneItemDetailResponse(content, photos);
	}

	private List<DoneItemPhotoResponse> getPhotoResponseIfExist(List<MultipartFile> photoFiles, Long itemId) {
		if (photoFiles != null && !photoFiles.isEmpty()) {
			return createDoneItemPhoto(itemId, photoFiles);
		}
		return Collections.emptyList();
	}

	private DoneItemResponse createDoneItemContent(Long userId, CreateDoneItemRequest request) {
		return doneListRepository.save(DoneList.builder()
			.userId(userId)
			.title(request.title())
			.iconUrl(request.iconUrl())
			.content(request.content())
			.doneDate(stringToDate(request.doneDate()))
			.build()
		).toDto();
	}

	private List<DoneItemPhotoResponse> createDoneItemPhoto(Long doneListId, List<MultipartFile> photoFiles) {
		List<Photo> photos = photoFiles.stream()
			.map(photoFile -> new Photo(uploadPhoto(photoFile), doneListId))
			.collect(Collectors.toList());
		return getPhotoResponses(photoRepository.saveAll(photos));
	}

	private String uploadPhoto(MultipartFile file) {
		try {
			return s3Uploader.upload(file);
		} catch (IOException e) {
			throw new RuntimeException("Failed to upload photo", e);
		}
	}

	private List<DoneItemPhotoResponse> getPhotoResponses(List<Photo> photos) {
		return photos.stream()
			.map(photo -> photo.toDto())
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public DoneItemDetailResponse getDoneItem(Long itemId) {
		DoneItemResponse doneitem = getDoneListEntity(itemId).toDto();
		List<DoneItemPhotoResponse> photos = getPhotosEntity(itemId).stream()
			.map(Photo::toDto)
			.collect(Collectors.toList());

		return new DoneItemDetailResponse(doneitem, photos);
	}

	private DoneList getDoneListEntity(Long itemId) {
		return doneListRepository.findById(itemId)
			.orElseThrow(() -> new EntityNotFoundException("donelist item not found: " + itemId));
	}

	private List<Photo> getPhotosEntity(Long itemId) {
		return photoRepository.findByDoneListId(itemId);
	}

	@Transactional(readOnly = true)
	public DoneListResponse getDoneListByDate(Long userId, String date) {
		List<DoneList> doneLists = doneListRepository.findByUserIdAndDoneDate(userId, stringToDate(date));
		return new DoneListResponse(date, doneItemResponses(doneLists));
	}

	private List<DoneItemResponse> doneItemResponses(List<DoneList> doneLists) {
		return doneLists.stream()
			.map(DoneList::toDto)
			.collect(Collectors.toList());
	}

	private LocalDate stringToDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(date, formatter);
	}

	@Transactional
	public DoneItemDetailResponse updateDoneItem(Long itemId, UpdateDoneItemRequest request,
		List<MultipartFile> updatedPhotoFiles) {
		DoneList doneList = getDoneListEntity(itemId);

		request.title().ifPresent(doneList::updateTitle);
		request.content().ifPresent(doneList::updateContent);

		if (!request.deletedPhotoIds().isEmpty()) {
			deletePhotoEntities(request.deletedPhotoIds());
		}

		if (updatedPhotoFiles != null && updatedPhotoFiles.stream().anyMatch(file -> !file.isEmpty())) {
			createDoneItemPhoto(itemId, updatedPhotoFiles);
		}

		return getDoneItem(itemId);
	}

	@Transactional
	public void deleteDoneItem(Long itemId) {
		deleteDoneItemContent(itemId);
		deletePhotoEntities(getPhotosEntity(itemId).stream()
			.map(Photo::getId).toList());
	}

	private void deleteDoneItemContent(Long itemId) {
		doneListRepository.deleteById(itemId);
	}

	private void deletePhotoEntities(List<Long> photoIds) {
		photoRepository.deleteAllById(photoIds);
	}

}
