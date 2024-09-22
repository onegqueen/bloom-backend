package com.example.bloombackend.claude.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.bloombackend.claude.dto.EmotionScore;
import com.example.bloombackend.claude.dto.SentimentAnalysisDto;

@Service
public class ClaudeService {

	@Value("${claude.api.key}")
	private String apiKey;

	@Value("${claude.api.model}")
	private String modelName;

	@Value("${claude.api.anthropic-version}")
	private String anthropicVersion;

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private ClaudeResponseHandler claudeResponseHandler;

	public SentimentAnalysisDto callClaudeForSentimentAnalysis(String textToAnalyze) {
		String apiUrl = "https://api.anthropic.com/v1/messages";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("x-api-key", apiKey);
		headers.set("anthropic-version", anthropicVersion);

		JSONObject requestBody = new JSONObject();
		requestBody.put("model", modelName);
		requestBody.put("max_tokens", 1024);

		JSONObject message = new JSONObject();
		message.put("role", "user");
		message.put("content", analyzeEmotionPrompt(textToAnalyze));

		requestBody.put("messages", new JSONArray().put(message));

		HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

		String response = restTemplate.exchange(
			apiUrl,
			HttpMethod.POST,
			entity,
			String.class
		).getBody();

		System.out.println(claudeResponseHandler.processClaudeResponse(response));

		return getResponse(response);
	}

	public SentimentAnalysisDto getResponse(String apiResponse) {
		return parseSentimentString(claudeResponseHandler.processClaudeResponse(apiResponse));
	}

	private String analyzeEmotionPrompt(String content) {
		return "\"" + content + "\""
			+ "의 감정분석을 하고 타인에게 부정적 영향을 끼치는 여부를 판단해줘."
			+ "감정이 조금 부정적이라도 공감을 살 수 있거나 수위가 낮으면 부정적 영향은 낮게 평가해줘."
			+ "심한 욕설이 포함 된 경우는 부정적 영향을 높게 평가해줘"
			+ "응답 포멧은 [관련된 감정 3가지][퍼센트],[부정적영향여부(UPPER, MIDDLE, LOWER)] 를 다른 말은 포함하지말고"
			+ "| 관련된 감정 | 퍼센트 |\n"
			+ "|-------------|--------|\n"
			+ "| 우울함      | 70    |\n"
			+ "| 외로움      | 60    |\n"
			+ "| 부정적 영향여부 |\n"
			+ "| ------------ |\n"
			+ "|	 UPEER	  | 이 예시 포멧의 표만 보여줘";
	}

	public SentimentAnalysisDto parseSentimentString(String input) {
		List<EmotionScore> emotions = new ArrayList<>();

		String[] lines = input.split("\n");

		for (int i = 2; i < 5; i++) {
			String line = lines[i].trim();
			if (line.contains("|")) {
				String[] columns = line.split("\\|");
				if (columns.length >= 3) {
					EmotionScore score = new EmotionScore(columns[1].trim(), Integer.parseInt(columns[2].trim()));
					emotions.add(score);
				}
			}
		}

		String negativeImpactLine = lines[lines.length - 1].replace("|", "").trim();

		return new SentimentAnalysisDto(emotions, negativeImpactLine);
	}

}
