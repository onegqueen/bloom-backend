package com.example.bloombackend.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bloombackend.oauth.controller.dto.response.KakaoInfoResponse;
import com.example.bloombackend.user.entity.UserEntity;
import com.example.bloombackend.user.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Long findOrCreateUser(KakaoInfoResponse response) {
		return userRepository.findBySnsId(response.getProviderId())
			.map(UserEntity::getId)
			.orElseGet(() -> newUser(response));
	}

	private Long newUser(KakaoInfoResponse response) {
		UserEntity user = UserEntity.builder()
			.name(response.getNickname())
			.provider(response.getOAuthProvider())
			.snsId(response.getProviderId())
			.build();

		return userRepository.save(user).getId();
	}
}
