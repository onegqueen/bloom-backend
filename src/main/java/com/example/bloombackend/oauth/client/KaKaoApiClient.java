package com.example.bloombackend.oauth.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.bloombackend.oauth.OAuthProvider;
import com.example.bloombackend.oauth.controller.dto.response.KakaoInfoResponse;
import com.example.bloombackend.oauth.token.KakaoTokens;

@Component
public class KaKaoApiClient {
	private static final String GRANT_TYPE = "authorization_code";

	@Value("${oauth.kakao.uri.token-url}")
	private String tokenUrl;

	@Value("${oauth.kakao.uri.user-info-url}")
	private String userInfoUrl;

	@Value("${oauth.kakao.client-id}")
	private String clientId;

	@Value("${oauth.kakao.redirect-uri}")
	private String redirectUri;

	private final RestTemplate restTemplate;

	@Autowired
	public KaKaoApiClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public OAuthProvider oAuthProvider() {
		return OAuthProvider.KAKAO;
	}

	public String requestAccessToken(String authorizationCode) {
		String url = tokenUrl;

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("code", authorizationCode);
		body.add("grant_type", GRANT_TYPE);
		body.add("client_id", clientId);
		body.add("redirect_uri", redirectUri);

		HttpEntity<?> request = new HttpEntity<>(body, headers);

		KakaoTokens response = restTemplate.postForObject(url, request, KakaoTokens.class);
		assert response != null;
		return response.getAccessToken();
	}

	public KakaoInfoResponse requestOauthInfo(String accessToken) {
		String url = userInfoUrl;

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		httpHeaders.set("Authorization", "Bearer " + accessToken);

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("property_keys", "[\"kakao_account.profile\"]");

		HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

		return restTemplate.postForObject(url, request, KakaoInfoResponse.class);
	}

	public String getAuthUrl() {
		return "https://kauth.kakao.com/oauth/authorize?client_id=" + clientId +
			"&redirect_uri=" + redirectUri + "&response_type=code";
	}
}
