
# HandTrip

## WORKSPACE
- [**Notion**](https://seong-kai.notion.site/PROJECT-14dfcab0377c80ae8e20e6d8f35d869c?pvs=4)
- [**FN**](https://github.com/SeoKai/HandTrip_FN)
- [**AI**](https://github.com/SeoKai/TripChoseAI)
- [**DataCollector**](https://github.com/SeoKai/TripDataCollector)

##  **목표**

사용자들이 여행지 정보를 손쉽게 탐색하고 리뷰를 작성하며, 이를 기반으로 개인 맞춤형 여행 플래너를 생성할 수 있는 플랫폼 개발.

---

##  **주요 특징**

1. 여행지 중심의 사용자 리뷰 제공
2. 사용자 선호 데이터를 기반으로 여행지 추천.
3. 개인화된 프로필 및 여행 기록 관리.

---

##  **레퍼런스 플랫폼**

- [화해 (Hwahae)](https://www.hwahae.co.kr/)
- [StubbyPlanner](https://www.stubbyplanner.com/planner/planner_rt.asp?lang=)
- [Myro](https://www.myro.co.kr/)

---

## **MEMEBR**
| **NAME&nbsp;** | **ROLE**              | **SKILL**                           | **PART**                            | **DESCRIPTION** |
|----------|-----------------------|-------------------------------------|-------------------------------------|-----------------|
| 심성완      | BN / FN / AI / SERVER | SPRING BOOT, REACT, SQL, FLASK, AWS | 플래너, 데이터 콜렉팅 및 가공, AI모델 작성, 배포      | 조장              |
| 박재영      | BN / FN               | SPRING BOOT, REACT, SQL             | 리뷰, 장소, 좋아요, 메일 및 문자 발송, 파일 업로드 |                 |
| 이재용      | BN / FN               | SPRING BOOT, REACT, SQL             | 사용자, 사용자 프로필, Jwt Token, OAuth 인증   |                 |
| 김은성      | FN                    | REACT, FIGMA, CSS, HTML             | 메인페이지, 여행지 조회페이지, 여행지 상세페이지, 마이페이지  |             |
| 김가은      | FN                    | REACT, FIGMA, CSS, HTML             | 메인페이지, 플래너 작성,조회,수정 페이지, 마이페이지      |             |

---

### **HISTORY**


| **DATA**            | **OBJECT**                                                                                 |
|---------------------|--------------------------------------------------------------------------------------------|
| 24.11.30 ~ 24.12.01 | [프로젝트 기획안 및 사전조사](https://seong-kai.notion.site/14cfcab0377c8044a14ceb51fde83bae)          |
| 24.12.01 ~ 24.12.02 | [구조 설계](https://seong-kai.notion.site/70b2cb7fb18d49a5a030fb7891e21372)                    |    
| 24.12.03 ~ 24.12.06 | [ERD 다이어그램 작성](https://seong-kai.notion.site/ERD-150fcab0377c8035bc16df1f8fa031a0?pvs=74)  |
| 24.12.06 ~ 24.12.08 | [엔드포인트 정리 (API 명세)](https://seong-kai.notion.site/154fcab0377c803b83b2e7b159524940?pvs=74) |
| 24.12.06 ~ 24.12.09 | [우선순위 확립](https://seong-kai.notion.site/4c8cd6966f054a5d827b1437a2e56a78?pvs=74)           |
| 24.12.11 ~ 24.12.12 | [인터페이스 정의서 작성](https://seong-kai.notion.site/159fcab0377c8002a4bccc243cb32193?pvs=74)      | 
| 24.12.11 ~ 25.01.03 | [백엔드 코딩](https://seong-kai.notion.site/152fcab0377c805eb137ccf3e2932274)                   |
| 24.12.16 ~ 24.01.07 | [프론트 코딩](https://seong-kai.notion.site/152fcab0377c80c19743f5d8136af29b?pvs=74)            |
| 25.01.01 ~ 25.01.06 | [배포, 배포 환경 테스트](https://seong-kai.notion.site/152fcab0377c8020888deffbae0b2950?pvs=74)     |

---
##  **주요 기능**
### **1. 사용자**
- **Jwt 기반 로그인**
- **Oauth 인증 소셜 로그인**
  - google, naver, kakao 지원
- **회원 가입 및 탈퇴**
  - Email, 휴대폰 인증  

### **2. 여행지 탐색 및 검색**
- **복합 키워드 기반 탐색**
  - 키워드 기반 탐색
      - 검색어를 통한 여행지 검색
  - 카테고리별 분류
      - 관광명소, 랜드마크, 음식등
      - 최대 3가지 카테고리를 통한 검색
  - 위치 기반 탐색
      - 도시별 여행지 검색
  - 모든 조건을 사용한 복합적 탐색

- **여행지 상세 정보 제공**
  - 사진 및 설명
  - 운영 시간, 주요 명소, 주소
  - 주위의 다른 여행지와 식당
  - 관련된 리뷰

### **3. 여행지 리뷰**
- **리뷰 작성** 
  - 별점(1~5점)과 텍스트 리뷰 작성.
  - 사진 첨부(최대 3장).
- **리뷰 관리**
  - 작성한 리뷰를 프로필에서 확인
  - 수정 및 삭제 기능

### **4. 플래너**
- **플래너 작성**
  - 여행지 검색
  - 마커를 통해 상세 일정 순서 및 동선 확인
- **플래너 관리**
  - 작성한 플래너를 프로필에서 확인
  - 수정 및 삭제 기능

  
### **5. 개인화된 기능**
- **위시리스트**
  - 관심 여행지 저장.
- **여행 기록 관리**
  - 작성한 플래너 및 리뷰를 프로필에서 확인.
- **맞춤 여행지 추천**
  - 사용자의 선호 테마, 좋아요 데이터를 AI가 분석하여 추천.

### **6. 외부 API 활용**
- Google Places API 또는 TripAdvisor API를 이용해 여행지 초기 데이터 세팅.


---

## **기술 스택 및 아키텍처**

**백엔드-**
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"><br>
**프론트엔드-**
<img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=white"><br>
**데이터베이스-**
<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white"><br>
**배포-**
<img src="https://img.shields.io/badge/AWS-232F3E?style=for-the-badge&logo=amazonwebservices&logoColor=white"><br>
**인증-**
<img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white">
<img src="https://img.shields.io/badge/OAuth-3EAAAF?style=for-the-badge&logo=oauth&logoColor=white">
<img src="https://img.shields.io/badge/Google-4285F4?style=for-the-badge&logo=google&logoColor=white">
<img src="https://img.shields.io/badge/Kakao-FFCD00?style=for-the-badge&logo=kakaotalk&logoColor=black">
<img src="https://img.shields.io/badge/Naver-03C75A?style=for-the-badge&logo=naver&logoColor=white"><br>
**테스트서버-**
<img src="https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=JUnit5&logoColor=white"><br>

---

## 주요 END POINT 

### **1. User Domain Area**

| Method | Path  | Description                                        |
| -- |-------|----------------------------------------------------|
|POST| /user/register |회원가입을 합니다. Email과 휴대폰 번호로 발송되는 인증 문자를 통해 인증된 회원만 가입 가능 |
|POST| /user/find-Email |회원가입 시 입력한 휴대폰 번호로 회원 이메일을 찾는 서비스                   |
|POST|/user/duplicate-email|회원가입 시 이메일의 중복 여부를 확인하는 서비스                         |
|POST|/user/change-password|비밀번호 변경 변경 서비스                                      |
|POST|/api/userProfile/get|회원의 프로필 정보를 조회하는 서비스|
|PUT|/api/userProfile|회원의 프로필 정보를 수정 할 수 있는 서비스. 닉네임, 프로필 사진, 비밀번호 수정 가능|
|POST|/api/userProfile/uploadProfileImage|회원이 등록한 프로필 사진을 서버에 업로드 하는 서비스|
|GET|/api/userProfile/images/{filename}|회원이 등록한 프로필 사진을 서버에서 서빙|

### **2. Authentication Domain Area**
| Method | Path  | Description                          |
| -- |-------|--------------------------------------|
|POST|/api/auth/login| 로그인 시 Access Token과 Refresh Token을 발급해주는 서비스 |
|POST|/api/auth/logout| 로그아웃 시 Access Token과 파기하는 서비스        | 
|POST|/api/refresh| 토큰 만료 시 새로운 토큰을 발급해주는 서비스            |
|POST|/api/email/send| 인증 코드 또는 임시 비밀번호를 이메일을 통해 발급해주는 서비스  |
|POST|/api/email/verify| 인증 코드가 알맞는지 확인 하는 서비스                |
|POST|/api/phone/send-one| 인증 코드를 문자로 발급해주는 서비스                 |
|POST|/api/phone/verify| 인증 코드가 알맞는지 확인 하는 서비스                |

### **3. AI Domain Area**
| Method | Path  | Description                                 |
|--------|-------|---------------------------------------------|
| POST   |/api/ai/rating| 사용자의 설문조사를 통해 AI의 학습 모델의 기반이 될 점수를 저장하는 서비스 |
| POST   |/api/ai/verify| 비회원 상태의 사용자에게 식당을 제외한 랜덤 여행지를 추천해주는 서비스     |
| GET    |/api/ai/random-places| AI의 추천 여행지를 조회할 수 있는 서비스                    |

### **4. Location Domain Area**
| Method | Path  | Description                                                   |
| -- |-------|---------------------------------------------------------------|
|GET|/api/locations/searchLocation| 태그, 지역, 검색어등의 키워드를 입력받아 해당하는 여행지 목록을 조회할 수 있는 서비스             |
|GET|/api/locations/{locationId}| 특정 여행지의 정보를 조회 할 수 있는 서비스                                     |
|GET|/api/locations/getNearby| 특정 위치 주위 x km내의 여행지를 특정 태그를 기준으로 나누어 검색 할 수 있는 서비스 |
|GET|/region/getAll| 모든 지역 목록을 조회 할 수 있는 서비스                                       |
|GET|/tag/getAll| 모든 태크 목록을 조회 할 수 있는 서비스                                       |
|POST|/api/locationFavorite/add| 사용자의 특정 여행지에 대한 좋아요 정보를 저장하는 서비스                              |
|DELETE|/api/locationFavorite/delete| 사용자가 좋아요한 여행지의 좋아요 정보를 삭제하는 서비스                               |
|GET|/api/locationFavorite/userFavorite| 특정 사용자가 좋아요를 표시한 여행지들을 조회할 수 있는 서비스                           |
|GET|/api/locationFavorite/count/{locationId}| 특정 여행지에 대해 좋아요를 누른 사용자의 수를 조회 할 수 있는 서비스                      |

#### 특이사항
- 여행지의 경우 Python 기반의 콜렉터가 특정 조건을 만족하는 데이터를 Google Place API로 요청하여 받아온 후 데이터베이스에 저장하기 때문에 삽입,수정,삭제 서비스는 제공되지 않음

### **5. Planner Domain Area**
| Method | Path  | Description                                   |
|-------|-------|-----------------------------------------------|
| POST  |/api/planner/save| 사용자가 작성한 플래너를 저장하는 서비스|
| GET   |/api/planner/{id}| 특정 플래너의 정보를 조회 할 수 있는 서비스|
| GET   |/api/planner/user/plans|특정 사용자가 작성한 플래너의 목록을 조회 할 수 있는 서비스|
| PUT   |/api/planner/update|저장된 플래너의 정보를 수정 할 수 있는 서비스|
| DELETE |/api/planner/{id}|특정 플래너를 삭제 할 수 있는 서비스|

### **6. Review Domain Area**
| Method | Path  | Description                             |
|-------|-------|-----------------------------------------|
|POST|/reviews/create| 사용자가 작성한 리뷰를 저장하는 서비스                   |
|GET|/reviews/{reviewId}| 특정 리뷰를 조회 할 수 있는 서비스                    |
|PUT|/reviews/{reviewId}/edit| 저장된 리뷰의 정보를 수정 할 수 있는 서비스               |
|DELETE|/reviews/delete/{reviewId}| 특정 리뷰를 삭제 할 수 있는 서비스                    |
|GET|/reviews/getReviewsWithUser| 리뷰 목록을 작성한 사용자의 정보와 함께 조회 할 수 있는 서비스    |
|GET|/reviews/getReviewsWithLocation| 리뷰 목록을 작성된 여행지의 정보와 함께 조회 할 수 있는 서비스    |
|POST|/reviews/uploadReviewImage| 리뷰 작성 시 첨부한 사진을 서버에 저장 하는 서비스           |
|GET|/reviews/average/{locationId}| 특정 장소에 대해 작성된 리뷰들의 별점 평균을 조회 할 수 있는 서비스 |
|GET|/reviews/images/{filename}| 리뷰 작성시 서버에 저장된 리뷰 이미지들을 서빙              |

---
## DEPENDENCIES LIST
| CAT            | NAME                                           | DESCRIPTION                                               |
|----------------|------------------------------------------------|-----------------------------------------------------------|
| **BUILD TOOLS** | org.springframework.boot:spring-boot-starter-thymeleaf | Thymeleaf 템플릿 엔진을 지원하는 Spring Boot 스타터        |
| **BUILD TOOLS** | org.springframework.boot:spring-boot-starter-web | Spring Web을 위한 기본적인 의존성                         |
| **BUILD TOOLS** | org.springframework.boot:spring-boot-starter-security | Spring Security를 위한 기본적인 의존성                    |
| **BUILD TOOLS** | org.springframework.boot:spring-boot-starter-oauth2-resource-server | OAuth2 리소스 서버 지원                                   |
| **BUILD TOOLS** | org.thymeleaf.extras:thymeleaf-extras-springsecurity6 | Thymeleaf와 Spring Security 통합을 위한 추가 라이브러리    |
| **BUILD TOOLS** | org.springframework.security:spring-security-test | Spring Security 테스트를 위한 의존성                      |
| **BUILD TOOLS** | org.springframework.boot:spring-boot-starter-test | Spring Boot 테스트 스타터                                  |
| **BUILD TOOLS** | org.springframework.boot:spring-boot-starter-oauth2-client | OAuth2 클라이언트를 위한 Spring Boot 스타터               |
| **DATABASE**    | com.mysql:mysql-connector-j:8.3.0               | MySQL 데이터베이스 연결을 위한 JDBC 드라이버               |
| **DATABASE**    | org.springframework.boot:spring-boot-starter-data-jpa | JPA를 위한 Spring Boot 스타터                             |
| **DATABASE**    | org.springframework.boot:spring-boot-starter-data-redis | Redis 데이터 저장소 지원                                  |
| **DATABASE**    | org.springframework.boot:spring-boot-starter-data-redis-reactive | Reactive Redis 지원                                       |
| **SECURITY**    | io.jsonwebtoken:jjwt-api:0.11.2                | JWT 생성 및 검증을 위한 API 라이브러리                    |
| **SECURITY**    | io.jsonwebtoken:jjwt-impl:0.11.2                | JWT 구현체                                                 |
| **SECURITY**    | io.jsonwebtoken:jjwt-jackson:0.11.2             | Jackson을 사용한 JWT 라이브러리                           |
| **VALIDATION**  | org.springframework.boot:spring-boot-starter-validation | Spring Validation을 위한 기본적인 의존성                  |
| **VALIDATION**  | jakarta.validation:jakarta.validation-api:3.1.0 | Jakarta Validation API                                     |
| **VALIDATION**  | org.hibernate.validator:hibernate-validator:8.0.1.Final | Hibernate Validator (JSR-303 표준 구현)                    |
| **REST**        | com.fasterxml.jackson.core:jackson-core:2.18.1  | Jackson JSON 라이브러리 핵심 기능                         |
| **REST**        | com.fasterxml.jackson.core:jackson-databind:2.18.1 | Jackson 데이터 바인딩 기능                                |
| **REST**        | com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.18.1 | Jackson XML 데이터 포맷 지원                              |
| **REST**        | com.google.code.gson:gson:2.11.0               | Google Gson JSON 라이브러리                                |
| **REST**        | org.springframework.boot:spring-boot-starter-hateoas:3.4.0 | HATEOAS를 위한 Spring Boot 스타터                        |
| **UTIL**        | org.springframework.boot:spring-boot-starter-mail | 이메일 발송을 위한 Spring Boot 스타터                    |
| **UTIL**        | commons-fileupload:commons-fileupload:1.4      | 파일 업로드를 위한 Commons FileUpload 라이브러리          |
| **UTIL**        | commons-io:commons-io:2.8.0                    | 파일 I/O 관련 유틸리티                                     |
| **UTIL**        | net.nurigo:sdk:4.3.2                           | 문자 전송(CoolSMS) 관련 SDK                                |
| **UTIL**        | org.projectlombok:lombok                       | 자바 코드에서 반복적인 작업을 줄이기 위한 Lombok 라이브러리  |
| **TEST**        | org.mockito:mockito-core:4.5.1                  | Mockito 핵심 라이브러리                                   |
| **TEST**        | org.mockito:mockito-junit-jupiter:4.5.1         | JUnit 5에서 Mockito 사용을 위한 의존성                    |

---
## CLASS DIAGRAM

![KakaoTalk_20250108_174414221](https://github.com/user-attachments/assets/9707b7d7-e9d0-435d-a441-0352087184e0)


---
## ENTITY RELATIONSHIP DIAGRAM

![ERD](https://github.com/user-attachments/assets/58a08701-8dad-41c6-a896-e58c1321ddec)

---

## AWS ARCHITECTURE DIAGRAM
![KakaoTalk_20250108_181319894](https://github.com/user-attachments/assets/a8724ab1-d3af-4be5-a71a-0c0a07b03820)

---
## DIRECTORY STRUCTURE
```
  C:.
  ├─Back
  │  │  .gitattributes
  │  │  .gitignore
  │  │  build.gradle
  │  │  gradlew
  │  │  gradlew.bat
  │  │  README.md
  │  │  settings.gradle
  │  │
  │  ├─.gradle
  │  │  ├─8.11.1
  │  │  │  │  gc.properties
  │  │  │  │
  │  │  │  ├─checksums
  │  │  │  │      checksums.lock
  │  │  │  │
  │  │  │  ├─executionHistory
  │  │  │  │      executionHistory.lock
  │  │  │  │
  │  │  │  ├─expanded
  │  │  │  ├─fileChanges
  │  │  │  │      last-build.bin
  │  │  │  │
  │  │  │  ├─fileHashes
  │  │  │  │      fileHashes.lock
  │  │  │  │
  │  │  │  └─vcsMetadata
  │  │  ├─buildOutputCleanup
  │  │  │      buildOutputCleanup.lock
  │  │  │      cache.properties
  │  │  │
  │  │  └─vcs-1
  │  │          gc.properties
  │  │
  │  ├─.idea
  │  │      .gitignore
  │  │      .name
  │  │      compiler.xml
  │  │      gradle.xml
  │  │      material_theme_project_new.xml
  │  │      misc.xml
  │  │      vcs.xml
  │  │      workspace.xml
  │  │
  │  ├─gradle
  │  │  └─wrapper
  │  │          gradle-wrapper.jar
  │  │          gradle-wrapper.properties
  │  │
  │  ├─src
  │  │  ├─main
  │  │  │  ├─java
  │  │  │  │  └─TeamGoat
  │  │  │  │      └─TripSupporter
  │  │  │  │          │  TripSupporterApplication.java
  │  │  │  │          │
  │  │  │  │          ├─Config
  │  │  │  │          │  │  RestTemplateConfig.java
  │  │  │  │          │  │  SecurityConfig.java
  │  │  │  │          │  │  WebConfig.java
  │  │  │  │          │  │
  │  │  │  │          │  ├─auth
  │  │  │  │          │  │      JwtAuthenticationFilter.java
  │  │  │  │          │  │      JwtTokenProvider.java
  │  │  │  │          │  │
  │  │  │  │          │  └─Verify
  │  │  │  │          │          MailConfig.java
  │  │  │  │          │
  │  │  │  │          ├─Controller
  │  │  │  │          │  │  HomeController.java
  │  │  │  │          │  │
  │  │  │  │          │  ├─Ai
  │  │  │  │          │  │      AiController.java
  │  │  │  │          │  │
  │  │  │  │          │  ├─Auth
  │  │  │  │          │  │      AuthController.java
  │  │  │  │          │  │
  │  │  │  │          │  ├─Certification
  │  │  │  │          │  │      EmailController.java
  │  │  │  │          │  │      PhoneController.java
  │  │  │  │          │  │
  │  │  │  │          │  ├─Favorite
  │  │  │  │          │  │      UserLocationFavoriteController.java
  │  │  │  │          │  │
  │  │  │  │          │  ├─Location
  │  │  │  │          │  │  │  LocationController.java
  │  │  │  │          │  │  │  RegionController.java
  │  │  │  │          │  │  │  TagController.java
  │  │  │  │          │  │  │
  │  │  │  │          │  │  └─Util
  │  │  │  │          │  │          LocationControllerValidator.java
  │  │  │  │          │  │
  │  │  │  │          │  ├─Planner
  │  │  │  │          │  │      PlannerController.java
  │  │  │  │          │  │
  │  │  │  │          │  ├─Review
  │  │  │  │          │  │  │  ReviewController.java
  │  │  │  │          │  │  │
  │  │  │  │          │  │  └─Util
  │  │  │  │          │  │          ReviewContollerValidator.java
  │  │  │  │          │  │
  │  │  │  │          │  └─User
  │  │  │  │          │          UserController.java
  │  │  │  │          │          UserProfileController.java
  │  │  │  │          │
  │  │  │  │          ├─Domain
  │  │  │  │          │  ├─Dto
  │  │  │  │          │  │  ├─Ai
  │  │  │  │          │  │  │      AiUserDto.java
  │  │  │  │          │  │  │
  │  │  │  │          │  │  ├─Auth
  │  │  │  │          │  │  │      AuthDto.java
  │  │  │  │          │  │  │      TokenInfo.java
  │  │  │  │          │  │  │
  │  │  │  │          │  │  ├─Bookmark
  │  │  │  │          │  │  │      BookmarkPlannerDto.java
  │  │  │  │          │  │  │
  │  │  │  │          │  │  ├─Certification
  │  │  │  │          │  │  │      VerificationResponseDto.java
  │  │  │  │          │  │  │
  │  │  │  │          │  │  ├─Favorite
  │  │  │  │          │  │  │      UserFavoriteLocationsDto.java
  │  │  │  │          │  │  │
  │  │  │  │          │  │  ├─Location
  │  │  │  │          │  │  │      LocationDto.java
  │  │  │  │          │  │  │      LocationParamDto.java
  │  │  │  │          │  │  │      LocationResponseDto.java
  │  │  │  │          │  │  │      LocationSplitByTagDto.java
  │  │  │  │          │  │  │      LocationWithDistanceDto.java
  │  │  │  │          │  │  │      RegionDto.java
  │  │  │  │          │  │  │      TagDto.java
  │  │  │  │          │  │  │
  │  │  │  │          │  │  ├─Planner
  │  │  │  │          │  │  │      DailyPlanDto.java
  │  │  │  │          │  │  │      PlannerDto.java
  │  │  │  │          │  │  │      ToDoDto.java
  │  │  │  │          │  │  │
  │  │  │  │          │  │  ├─Review
  │  │  │  │          │  │  │      ReviewDto.java
  │  │  │  │          │  │  │      ReviewWithLocationDto.java
  │  │  │  │          │  │  │      ReviewWithUserProfileDto.java
  │  │  │  │          │  │  │
  │  │  │  │          │  │  ├─Tag
  │  │  │  │          │  │  │      PlannerTagDto.java
  │  │  │  │          │  │  │
  │  │  │  │          │  │  └─User
  │  │  │  │          │  │          ChangePasswordRequestDto.java
  │  │  │  │          │  │          UserAndProfileDto.java
  │  │  │  │          │  │          UserDto.java
  │  │  │  │          │  │          UserProfileDto.java
  │  │  │  │          │  │
  │  │  │  │          │  ├─Entity
  │  │  │  │          │  │  ├─Ai
  │  │  │  │          │  │  │      AiUser.java
  │  │  │  │          │  │  │
  │  │  │  │          │  │  ├─Auth
  │  │  │  │          │  │  │      AuthToken.java
  │  │  │  │          │  │  │
  │  │  │  │          │  │  ├─Certification
  │  │  │  │          │  │  │      VerificationCode.java
  │  │  │  │          │  │  │
  │  │  │  │          │  │  ├─Favorite
  │  │  │  │          │  │  │      UserLocationFavorite.java
  │  │  │  │          │  │  │
  │  │  │  │          │  │  ├─Location
  │  │  │  │          │  │  │      Location.java
  │  │  │  │          │  │  │      LocationTag.java
  │  │  │  │          │  │  │      Region.java
  │  │  │  │          │  │  │      Tag.java
  │  │  │  │          │  │  │
  │  │  │  │          │  │  ├─Planner
  │  │  │  │          │  │  │      DailyPlan.java
  │  │  │  │          │  │  │      Planner.java
  │  │  │  │          │  │  │      ToDo.java
  │  │  │  │          │  │  │
  │  │  │  │          │  │  ├─Review
  │  │  │  │          │  │  │      Review.java
  │  │  │  │          │  │  │      ReviewImage.java
  │  │  │  │          │  │  │
  │  │  │  │          │  │  └─User
  │  │  │  │          │  │          User.java
  │  │  │  │          │  │          UserProfile.java
  │  │  │  │          │  │
  │  │  │  │          │  ├─Enum
  │  │  │  │          │  │      ReviewStatus.java
  │  │  │  │          │  │      UserRole.java
  │  │  │  │          │  │      UserStatus.java
  │  │  │  │          │  │
  │  │  │  │          │  └─User
  │  │  │  │          │          UserAndProfileDto.java
  │  │  │  │          │          UserDto.java
  │  │  │  │          │          UserProfileDto.java
  │  │  │  │          │
  │  │  │  │          ├─Exception
  │  │  │  │          │  │  DuplicateEmailException.java
  │  │  │  │          │  │  ExceptionHandler.java
  │  │  │  │          │  │  GlobalExceptionHandler.java
  │  │  │  │          │  │  IllegalPageRequestException.java
  │  │  │  │          │  │  IllegalSortRequestException.java
  │  │  │  │          │  │  UnsupportedFileTypeException.java
  │  │  │  │          │  │  UserExceptionHandler.java
  │  │  │  │          │  │  UserNotFoundException.java
  │  │  │  │          │  │
  │  │  │  │          │  ├─Favorite
  │  │  │  │          │  │      DuplicateFavoriteException.java
  │  │  │  │          │  │      FavoriteNotFoundException.java
  │  │  │  │          │  │
  │  │  │  │          │  ├─Location
  │  │  │  │          │  │      IllegalLocationIdException.java
  │  │  │  │          │  │      LocationExceptionHandler.java
  │  │  │  │          │  │      LocationInvalidDistanceException.java
  │  │  │  │          │  │      LocationInvalidLatitudeException.java
  │  │  │  │          │  │      LocationInvalidLongitudeException.java
  │  │  │  │          │  │      LocationInvalidTagNamesException.java
  │  │  │  │          │  │      LocationLatAndLonNullException.java
  │  │  │  │          │  │      LocationMappingException.java
  │  │  │  │          │  │      LocationNotFoundException.java
  │  │  │  │          │  │      LocationNullException.java
  │  │  │  │          │  │      LocationPageRequestNullException.java
  │  │  │  │          │  │      LocationRegionIdException.java
  │  │  │  │          │  │      LocationSearchKeywordNullException.java
  │  │  │  │          │  │      LocationSortNullException.java
  │  │  │  │          │  │
  │  │  │  │          │  ├─Review
  │  │  │  │          │  │      MismatchedException.java
  │  │  │  │          │  │      ReviewAuthorMismatchException.java
  │  │  │  │          │  │      ReviewCommentNullException.java
  │  │  │  │          │  │      ReviewDtoNullException.java
  │  │  │  │          │  │      ReviewException.java
  │  │  │  │          │  │      ReviewExceptionHandler.java
  │  │  │  │          │  │      ReviewNotFoundException.java
  │  │  │  │          │  │      ReviewRatingNullException.java
  │  │  │  │          │  │      ReviewStatusMismatchException.java
  │  │  │  │          │  │
  │  │  │  │          │  └─userProfile
  │  │  │  │          │          UserProfileException.java
  │  │  │  │          │          UserProfileExceptionHandler.java
  │  │  │  │          │
  │  │  │  │          ├─Mapper
  │  │  │  │          │  ├─Ai
  │  │  │  │          │  │      AiUserMapper.java
  │  │  │  │          │  │
  │  │  │  │          │  ├─Location
  │  │  │  │          │  │      LocationMapper.java
  │  │  │  │          │  │      RegionMapper.java
  │  │  │  │          │  │      TagMapper.java
  │  │  │  │          │  │
  │  │  │  │          │  ├─Planner
  │  │  │  │          │  │      DailyPlanMapper.java
  │  │  │  │          │  │      PlannerMapper.java
  │  │  │  │          │  │      ToDoMapper.java
  │  │  │  │          │  │
  │  │  │  │          │  └─Review
  │  │  │  │          │          ReviewMapper.java
  │  │  │  │          │
  │  │  │  │          ├─Repository
  │  │  │  │          │  ├─Ai
  │  │  │  │          │  │      AiUserRepository.java
  │  │  │  │          │  │
  │  │  │  │          │  ├─Auth
  │  │  │  │          │  │      AuthTokenRepository.java
  │  │  │  │          │  │
  │  │  │  │          │  ├─Certification
  │  │  │  │          │  │      VerificationCodeRepository.java
  │  │  │  │          │  │
  │  │  │  │          │  ├─Favorite
  │  │  │  │          │  │      UserLocationFavoriteRepository.java
  │  │  │  │          │  │
  │  │  │  │          │  ├─Location
  │  │  │  │          │  │      LocationRepository.java
  │  │  │  │          │  │      RegionRepository.java
  │  │  │  │          │  │      TagRepository.java
  │  │  │  │          │  │
  │  │  │  │          │  ├─Planner
  │  │  │  │          │  │      DailyPlanRepository.java
  │  │  │  │          │  │      PlannerRepository.java
  │  │  │  │          │  │      ToDoRepository.java
  │  │  │  │          │  │
  │  │  │  │          │  ├─Review
  │  │  │  │          │  │      ReviewImageRepository.java
  │  │  │  │          │  │      ReviewRepository.java
  │  │  │  │          │  │
  │  │  │  │          │  └─User
  │  │  │  │          │          UserProfileRepository.java
  │  │  │  │          │          UserRepository.java
  │  │  │  │          │
  │  │  │  │          └─Service
  │  │  │  │              │  ReviewService.java
  │  │  │  │              │  TravelPlaceService.java
  │  │  │  │              │  UserProfileService.java
  │  │  │  │              │  UserService.java
  │  │  │  │              │
  │  │  │  │              ├─Ai
  │  │  │  │              │      AiModelIntegrationService.java
  │  │  │  │              │      AiRecommendationService.java
  │  │  │  │              │
  │  │  │  │              ├─Auth
  │  │  │  │              │      AuthService.java
  │  │  │  │              │      AuthServiceImpl.java
  │  │  │  │              │      CustomOAuth2User.java
  │  │  │  │              │      CustomOAuth2UserService.java
  │  │  │  │              │      CustomUserDetailsService.java
  │  │  │  │              │      TokenCleanupScheduler.java
  │  │  │  │              │
  │  │  │  │              ├─Certification
  │  │  │  │              │      EmailService.java
  │  │  │  │              │      PhoneService.java
  │  │  │  │              │
  │  │  │  │              ├─Favorite
  │  │  │  │              │      UserLocationFavoriteService.java
  │  │  │  │              │
  │  │  │  │              ├─Location
  │  │  │  │              │  │  LocationServiceImpl.java
  │  │  │  │              │  │  RegionService.java
  │  │  │  │              │  │  TagService.java
  │  │  │  │              │  │
  │  │  │  │              │  └─Util
  │  │  │  │              │          LocationServiceValidator.java
  │  │  │  │              │          PhotoUrlGenerator.java
  │  │  │  │              │
  │  │  │  │              ├─Planner
  │  │  │  │              │      PlannerService.java
  │  │  │  │              │
  │  │  │  │              ├─Review
  │  │  │  │              │  │  ReviewService.java
  │  │  │  │              │  │  ReviewServiceImpl.java
  │  │  │  │              │  │
  │  │  │  │              │  └─Util
  │  │  │  │              │          ReviewServiceValidator.java
  │  │  │  │              │
  │  │  │  │              └─User
  │  │  │  │                  │  UserProfileService.java
  │  │  │  │                  │  UserProfileServiceImpl.java
  │  │  │  │                  │  UserService.java
  │  │  │  │                  │  UserServiceImpl.java
  │  │  │  │                  │
  │  │  │  │                  └─Util
  │  │  │  │                          RandomStringGenerator.java
  │  │  │  │
  │  │  │  └─resources
  │  │  │      │  application.yml
  │  │  │      │
  │  │  │      ├─static
  │  │  │      │      favicon.ico
  │  │  │      │
  │  │  │      └─templates
  │  │  │              index.html
  │  │  │
  │  │  └─test
  │  │      └─java
  │  │          └─TeamGoat
  │  │              └─TripSupporter
  │  │                  │  TripSupporterApplicationTests.java
  │  │                  │
  │  │                  ├─Controller
  │  │                  │      LocationControllerTest.java
  │  │                  │      PlannerControllerTest.java
  │  │                  │      UserControllerTest.java
  │  │                  │
  │  │                  ├─Repository
  │  │                  │      LocationRepositoryTest.java
  │  │                  │      ReviewRepositoryTest.java
  │  │                  │
  │  │                  └─Service
  │  │                      │  LocationServiceTest.java
  │  │                      │  ReviewServiceTest.java
  │  │                      │  UserLocationFavoriteRepositoryTest.java
  │  │                      │  UserServiceImplTest.java
  │  │                      │
  │  │                      └─Planner
  │  │                              PlannerServiceTest.java
  │  │
  │  └─upload
  │      └─images
  │          ├─profile
  │          │      default_profile_image.png
  │          │      고유한사진이름견본.png
  │          │
  │          └─review
  │                  고유한사진이름견본.png
  │
  └─Front
  │  .env
  │  .gitignore
  │  package-lock.json
  │  package.json
  │  README.md
  │
  ├─.idea
  │  │  .gitignore
  │  │  jpa.iml
  │  │  material_theme_project_new.xml
  │  │  modules.xml
  │  │  traveplan.iml
  │  │  vcs.xml
  │  │  workspace.xml
  │  │
  │  └─inspectionProfiles
  │          Project_Default.xml
  │
  ├─public
  │      favicon.ico
  │      index.html
  │      logo192.png
  │      logo512.png
  │      manifest.json
  │      robots.txt
  │
  └─src
  │  App.css
  │  App.js
  │  index.css
  │  index.js
  │  index.zip
  │
  ├─api
  │      ApiClient.js
  │      AuthService.js
  │      UserService.js
  │
  ├─component
  │  │  CalendarModal.css
  │  │  CalendarModal.jsx
  │  │  Card.css
  │  │  Card.jsx
  │  │  Footer.css
  │  │  Footer.jsx
  │  │  LogoutButton.jsx
  │  │  MenuBar.css
  │  │  MenuBar.jsx
  │  │  modal.css
  │  │  modal.jsx
  │  │  PlanButton.jsx
  │  │  RandomPlaces.css
  │  │  RandomPlaces.jsx
  │  │  ReviewCreateModal.css
  │  │  ReviewCreateModal.jsx
  │  │
  │  ├─MyPage
  │  │      AccountDeleteModal.css
  │  │      AccountDeleteModal.jsx
  │  │      ChangePasswordModal.css
  │  │      ChangePasswordModal.jsx
  │  │      ProfileEditModal.css
  │  │      ProfileEditModal.jsx
  │  │
  │  └─PlanTrip
  │          DailyPlanList.jsx
  │          MapRenderer.jsx
  │          PolylineRenderer.jsx
  │          SaveModal.jsx
  │          usePlanData.jsx
  │
  ├─img
  │  │  AttractionBannerImg.jpg
  │  │  back.png
  │  │  cross.png
  │  │  Culture.jpg
  │  │  Dotonbori.jpg
  │  │  Food.jpg
  │  │  Fukuoka.jpg
  │  │  FukuokaTower.jpg
  │  │  FushimiInariShrine.jpg
  │  │  google.png
  │  │  Harajuku.png
  │  │  Instagram.png
  │  │  kakao.png
  │  │  KyotoCity.jpg
  │  │  Landmark.jpg
  │  │  MainBanner.jpg
  │  │  MainLogo.png
  │  │  map.jpg
  │  │  map.png
  │  │  naver.png
  │  │  Osaka.jpg
  │  │  OsakaCastle.jpg
  │  │  PlanImg.jpg
  │  │  planListImg.jpg
  │  │  SecondBanner.jpg
  │  │  Sensoji.jpg
  │  │  Shopping.jpg
  │  │  ThirdBanner.jpg
  │  │  Tokyo.jpg
  │  │  TokyoTower.jpg
  │  │  TouristAttraction.jpg
  │  │  youtube.png
  │  │
  │  └─icons
  │          angleDoubleSmallLeft.png
  │          angleDoubleSmallRight.png
  │          angleSmallLeft.png
  │          angleSmallRight.png
  │          arrowSmallLeft.png
  │          back.png
  │          cross.png
  │          google.png
  │          heart.png
  │          heartFilled.png
  │          Instagram.png
  │          kakao.png
  │          naver.png
  │          person.png
  │          star.png
  │          starColor.png
  │          youtube.png
  │
  ├─pages
  │  ├─Ai
  │  │      EvaluatePlaces.css
  │  │      EvaluatePlaces.jsx
  │  │
  │  ├─Attractions
  │  │      AttractionDetail.css
  │  │      AttractionDetail.jsx
  │  │      Attractions.css
  │  │      Attractions.jsx
  │  │
  │  ├─community
  │  │      Community.jsx
  │  │
  │  ├─Home
  │  │      Home.css
  │  │      Home.jsx
  │  │
  │  ├─MyPage
  │  │      MyPage.css
  │  │      MyPage.jsx
  │  │
  │  ├─plan
  │  │      EditPlan.jsx
  │  │      Plan.css
  │  │      Plan.jsx
  │  │      PlannerList.css
  │  │      PlannerList.jsx
  │  │      PlanTrip.css
  │  │      PlanTrip.jsx
  │  │      SelectDates.jsx
  │  │      ViewPlan.css
  │  │      ViewPlan.jsx
  │  │
  │  ├─test
  │  │      PlannerList.css
  │  │      PlannerList.jsx
  │  │      RandomPlaces.css
  │  │      RandomPlaces.jsx
  │  │
  │  └─User
  │          FindId.css
  │          FindId.jsx
  │          FindPw.css
  │          FindPw.jsx
  │          OAuth2Callback.jsx
  │          SignIn.css
  │          SignIn.jsx
  │          SignUp.css
  │          SignUp.jsx
  │
  └─styles
  reset.css
