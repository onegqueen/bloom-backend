package com.example.bloombackend.oauth.controller.dto.response;

import com.example.bloombackend.oauth.OAuthProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoInfoResponse(
	@JsonProperty("kakao_account") KakaoAccount kakaoAccount,
	@JsonProperty("id") Long providerId
) {

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record KakaoAccount(
		KakaoProfile profile,
		String email
	) {
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record KakaoProfile(
		String nickname
	) {
	}

	public String getNickname() {
		return kakaoAccount.profile().nickname();
	}

	public String getProviderId() {
		return providerId.toString();
	}

	public OAuthProvider getOAuthProvider() {
		return OAuthProvider.KAKAO;
	}
}
