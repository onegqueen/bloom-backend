package com.example.bloombackend.oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloombackend.oauth.service.OAuthLoginService;

@RestController
@RequestMapping("/api/auth")
public class OAuthController {
	private final OAuthLoginService oAuthLoginService;

	@Autowired
	public OAuthController(OAuthLoginService oAuthLoginService) {
		this.oAuthLoginService = oAuthLoginService;
	}

	@GetMapping("/oauth/kakao")
	public ResponseEntity<String> loginKakao(@RequestParam String code) {
		return ResponseEntity.ok(oAuthLoginService.login(code));
	}

	@GetMapping("/kakao/login")
	public String redirectToKakaoLogin() {
		return oAuthLoginService.getKakaoLoginUrl();
	}
}
