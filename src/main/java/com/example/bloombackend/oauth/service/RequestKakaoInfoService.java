package com.example.bloombackend.oauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.bloombackend.oauth.client.KaKaoApiClient;
import com.example.bloombackend.oauth.controller.dto.response.KakaoInfoResponse;

@Component
public class RequestKakaoInfoService {
	private final KaKaoApiClient client;

	@Autowired
	public RequestKakaoInfoService(KaKaoApiClient client) {
		this.client = client;
	}

	public KakaoInfoResponse request(String authorizationCode) {
		String accessToken = client.requestAccessToken(authorizationCode);
		return client.requestOauthInfo(accessToken);
	}

	public String getRedirectUri() {
		return client.getAuthUrl();
	}
}
