# 일상에서 찾는 작은 기쁨, bloom과 함께 - backend
![bloom_slogan_trans](https://github.com/user-attachments/assets/02ecaacb-77a8-449b-baa8-d798aa98f47c)
![image](https://github.com/user-attachments/assets/de8bf0db-9378-4726-9029-918fcbc48239)


## **1. 프로젝트 소개**

<aside>
💡

번아웃으로 인해 무기력함을 경험하고 일상적인 활동조차 어려움을 겪는 사람들에게 작은 성취감을 제공하여 **일상으로의 복귀를 돕는 것을 목적**으로 합니다. 무기력증 및 우울증 치료에 주로 사용되는 **행동활성화(action activation) 기반의 미션**들을 통해 자기효능감을 점진적으로 높일 수 있도록 설계되습니다.

</aside>

### ✅ 주요화면
![image](https://github.com/user-attachments/assets/29831e9e-0c34-4b63-a052-08316edca84f)
![image](https://github.com/user-attachments/assets/9ccc990d-74cd-4f44-b67b-caf0b8fbd15d)
![image](https://github.com/user-attachments/assets/19f871d2-54e3-4c3e-83c2-a3ba932841ac)
![image](https://github.com/user-attachments/assets/4c4610cd-be1c-41e6-b253-666147fff148)
![image](https://github.com/user-attachments/assets/9e229337-094d-434a-b98b-b9c3098a98f0)
![image](https://github.com/user-attachments/assets/3b482405-bff5-430e-9945-5efea2ee35d8)
![image](https://github.com/user-attachments/assets/2466fb60-c3f6-43f7-bde0-01976c58b9be)

### ✅ 주요 기능

- **유리병 메시지**
    
    ‘유리병 메시지’는 사용자가 익명의 다른 사용자에게 응원과 위로의 메시지를 남길 수 있는 기능이다. 서로 답장을 주고받을수는 없지만, 더욱 더 안전한 공간에서 메시지를 남길 수 있어 사용자들이 자유롭게 응원과 위로를 전달할 수 있다. 또한 AI 알고리즘을 통해 안전하고 긍정적인 메시지들이 전달되도록 하였다.
    
- **퀘스트**
    
    '퀘스트'란 이불 개기, 가까운 곳 산책하기 등 사용자의 무기력함을 해소하기 위해 설계된 행동활성화 치료 기법 기반의 간단한 미션들로, 사용자가 일상 속에서 단계별로 성취감을 느낄 수 있도록 도와준다. 사용자가 퀘스트를 완료하면 일별 성취도 꽃이 피는 형상으로 확인할 수 있어 진행 상황을 보다 직관적으로 파악할 수 있다. 또한, 사용자 맞춤 데이터를 기반으로 한 AI 추천 퀘스트 기능이 포함되어 있어 더욱 개인화된 경험을 제공한다.
    
- **오늘의 질문**
    
    '오늘의 질문'은 사용자에게 자아 성찰을 도울 수 있는 질문을 제공하며, 답변을 통해 자신의 생각을 정리하고 더 깊이 있는 성찰을 할 수 있도록 돕는다.
    
- **던 리스트**
    
    '던 리스트'는 사용자가 하루 동안 해낸 일들을 되돌아보며 기록할 수 있는 기능이다. 해야 할 일을 미리 적어두고 압박감을
    주는 기존의 '투두 리스트'와는 달리, 완료한 일을 기록함으로써 작은 성취감과 만족감을 느낄 수 있도록 설계되었다.
    
- **월간 리포트**
    
    '월간 리포트'에서는 사용자가 한달 간 피운 꽃들이 정원 형상으로 시각적으로 표현되며, 성취도를 분석하여 맞춤형 AI 통계 정보를 제공한다. 이를 통해 사용자는 더욱 동기부여를 받을 수 있고, 분석 결과를 통해 자신의 발전을 확인하며 더 나은 방향으로 나아갈 수 있다.
    

## 2. 기술 스택

### ✅ DB

- `Mysql`
- `Firebase`

### ✅ Backend

- `Spring boot`
- `Spring Data JPA`
- `Query Dsl`

### ✅ Frontend

- `React`

### ✅ 협업

- `Github`
- `Notion`

---

## 3. ERD

### 전체

![image](https://github.com/user-attachments/assets/b9a8d9db-8a27-49e7-b5ed-3c318bc69fd3)


### 사용자

<img src="https://github.com/user-attachments/assets/22839c4f-415d-4d86-969e-ee5e655295e5" width="30%">


### 유리병 메시지

<img src="https://github.com/user-attachments/assets/6ee90c54-cd79-4251-943d-80e1c085cf66" width="40%">

### 던 리스트

<img src="https://github.com/user-attachments/assets/42b2436d-a8e6-41fd-b443-492bb806e82f" width="40%">

### 오늘의 질문 & 퀘스트

<img src="https://github.com/user-attachments/assets/6cc27403-97a4-4b0c-9611-f855129cfcbf" width="40%">


### 아이템

<img src="https://github.com/user-attachments/assets/16c45e24-32aa-4441-acd1-9f00497cdbed" width="40%">


## 4. 담당 개발 내용

### ERD 설계

- 주요 로직 조회 성능을 고려한 erd 설계
    - 유리병 메시지
        - 작성된 메시지 테이블과 수신기록 테이블 분리로 메시지 전송 쿼리 성능 개선
        - 유리병 메시지 반응 테이블
    - 던리스트
        - 던리스트와 사진 테이블

### 담당 API RestDocs 작성

- 문서화
- 테스트 기반 명세서로 신뢰도 향상

### 회원가입 및 소셜 로그인
    
- 소셜로그인
    - `RestTemplate`을 활용한 `OAuth` 로그인 로직 직접 구현

### 유리병 메시지 API
    
- 유리병 메시지 api 주요 개발 내용
    - **QueryDsl**을 활용한 랜덤 메시지 **쿼리 최적화**
        - ✅ **조건**
            - 자신이 보낸 메시지는 제외할 것
            - 수신한 적 있는 메시지는 제외할 것
            - 위험도가 ‘상’으로 분류된 메시지는 필터링 할 것
        - ✅ **성능개선**
            - 방안 1. **데이터베이스 수준의 randomOrder**
                - 서버 부하 감소
            - 방안 2. **캐싱 (논의중 성능 테스트 필요)**
                - 좋아요 반응 개수 기반 캐싱
                - 일정 시간마다 적정개수의 메시지 캐싱
                    - 캐싱된 메시지중 조건에 맞는 메시지가 없다면 DB탐색
                    - 조건에 맞는 메시지가 없다면 서버 디폴트 메시지 전송
    - LLM 활용 **프롬프트 엔지니어링**을 통한 유리병 메시지 감정 분석 및 필터링
        
        ```java
        public class AnalyzeMessagePrompt {
        	public static final String ANALYZE_MESSAGE_PROMPT = "%s"
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
        		+ "|	 UPPER	  | 이 예시 포멧의 표만 보여줘";
        }
        
        ```
