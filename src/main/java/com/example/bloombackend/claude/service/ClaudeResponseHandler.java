package com.example.bloombackend.claude.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ClaudeResponseHandler {

	public String processClaudeResponse(String jsonResponse) {
		JSONObject jsonObject = new JSONObject(jsonResponse);

		JSONArray choicesArray = jsonObject.getJSONArray("content");
		if (choicesArray.length() > 0) {
			JSONObject content = choicesArray.getJSONObject(0);

			return content.getString("text");
		}

		return "No response from Claude.";
	}
}

