package com.example.bloombackend.oauth.service;

import com.example.bloombackend.global.config.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bloombackend.oauth.controller.dto.response.KakaoInfoResponse;
import com.example.bloombackend.user.service.UserService;

@Service
public class OAuthLoginService {
	private final RequestKakaoInfoService requestKakaoInfoService;
	private final UserService userService;
	private final JwtTokenProvider jwtTokenProvider;

	@Autowired
	private OAuthLoginService(RequestKakaoInfoService requestKakaoInfoService, UserService userService, JwtTokenProvider jwtTokenProvider) {
		this.requestKakaoInfoService = requestKakaoInfoService;
		this.userService = userService;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	public String login(String authorizationCode) {
		KakaoInfoResponse response = requestKakaoInfoService.request(authorizationCode);
		Long userId = userService.findOrCreateUser(response);
		return jwtTokenProvider.createAccessToken(userId.toString());
	}

	public String getKakaoLoginUrl() {
		return requestKakaoInfoService.getRedirectUri();
	}
}
