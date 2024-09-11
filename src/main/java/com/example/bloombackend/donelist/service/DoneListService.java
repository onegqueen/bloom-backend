package com.example.bloombackend.donelist.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bloombackend.donelist.controller.dto.request.CreateDoneItemRequest;
import com.example.bloombackend.donelist.controller.dto.response.DoneItemDetailResponse;
import com.example.bloombackend.donelist.entity.DoneList;
import com.example.bloombackend.donelist.repository.DoneListRepository;

@Service
public class DoneListService {
	private final DoneListRepository doneListRepository;

	public DoneListService(DoneListRepository doneListRepository) {
		this.doneListRepository = doneListRepository;
	}

	@Transactional
	public DoneItemDetailResponse createDoneItem(Long userId, CreateDoneItemRequest request) {
		return doneListRepository.save(
			DoneList.builder()
				.userId(userId)
				.title(request.title())
				.iconUrl(request.iconUrl())
				.photoUrl(request.photoUrl())
				.content(request.content()).build()
		).toDto();
	}

}
