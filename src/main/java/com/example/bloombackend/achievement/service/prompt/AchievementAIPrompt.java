package com.example.bloombackend.achievement.service.prompt;

public class AchievementAIPrompt {
    public static final String ACHIEVEMENT_SUMMARY_PROMPT = """
        다음은 사용자의 최근 6개월간 성취도 데이터입니다.:
        %s
        평균으로 %.2f개의 꽃이 핌.
        이 데이터를 바탕으로 사용자의 6개월 성취도에 대한 총평을 작성해주세요.
        사용자의 성취도 데이터를 분석하고 간결하게 총평을 작성해주세요.
        실제 앱 서비스의 월간 리포트 페이지에 사용될 내용입니다.
        인사말이나 불필요한 표현을 사용하지 말고,
        직접적으로 분석 결과만 제공해주세요.
        너무 딱딱하지 않게 "~입니다" 대신 "~에요" 어미로 작성해주세요.
        마지막에는 이모지를 넣어 친근하게 마무리해주세요.
        """;
}