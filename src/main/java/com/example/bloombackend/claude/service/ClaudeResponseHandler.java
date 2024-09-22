package com.example.bloombackend.claude.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ClaudeResponseHandler {

	// Claude API 응답 처리 메서드
	public String processClaudeResponse(String jsonResponse) {
		// 응답 JSON을 파싱하여 JSONObject로 변환
		JSONObject jsonObject = new JSONObject(jsonResponse);

		// choices 배열에서 첫 번째 선택지의 message.content를 가져옴
		JSONArray choicesArray = jsonObject.getJSONArray("content");
		if (choicesArray.length() > 0) {
			JSONObject content = choicesArray.getJSONObject(0);

			return content.getString("text"); // Claude의 응답 반환
		}

		return "No response from Claude."; // 응답이 없을 경우
	}
}

