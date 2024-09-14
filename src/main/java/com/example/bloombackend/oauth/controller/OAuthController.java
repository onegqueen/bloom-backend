package com.example.bloombackend.oauth.controller;

import com.example.bloombackend.oauth.controller.dto.request.KakaoLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.bloombackend.oauth.controller.dto.response.LoginResponse;
import com.example.bloombackend.oauth.service.OAuthLoginService;

@RestController
@RequestMapping("/api/auth")
public class OAuthController {
	private final OAuthLoginService oAuthLoginService;

	@Autowired
	public OAuthController(OAuthLoginService oAuthLoginService) {
		this.oAuthLoginService = oAuthLoginService;
	}

	@PostMapping("/kakao")
	public ResponseEntity<LoginResponse> loginKakao(@RequestBody KakaoLoginRequest request) {
		return ResponseEntity.ok(oAuthLoginService.login(request.authorizationCode()));
	}

	@GetMapping("/kakao/login")
	public String redirectToKakaoLogin() {
		return oAuthLoginService.getKakaoLoginUrl();
	}
}
