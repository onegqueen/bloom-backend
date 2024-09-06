package com.example.bloombackend.oauth.token;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KakaoTokens {
	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("refresh_token")
	private String refreshToken;

	@JsonProperty("expires_in")
	private String expiresIn;

	@JsonProperty("refresh_token_expires_in")
	private String refreshTokenExpiresIn;

	@JsonProperty("scope")
	private String scope;

	public KakaoTokens() {
	}

	public String getAccessToken() {
		return accessToken;
	}
}
