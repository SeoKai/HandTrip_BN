## WORKSPACE
- [**Notion**](https://seong-kai.notion.site/PROJECT-14dfcab0377c80ae8e20e6d8f35d869c?pvs=4)

## 프로젝트 이름 JPA HandTrip

#### **사용자가 자유롭게 일본 여행지 정보를 리뷰하고, 이를 기반으로 최적화된 여행 플래너를 작성할 수 있는 플랫폼**
---

## 🏆 **프로젝트 목표**

사용자들이 여행지 정보를 손쉽게 탐색하고 리뷰를 작성하며, 이를 기반으로 개인 맞춤형 여행 플래너를 생성할 수 있는 플랫폼 개발.

---

## 🌟 **주요 특징**

1. 여행지 중심의 사용자 리뷰와 댓글 시스템 제공.
2. 사용자 선호 데이터를 기반으로 여행지 추천.
3. 개인화된 프로필 및 여행 기록 관리.

---

## 📌 **레퍼런스 플랫폼**

- [화해 (Hwahae)](https://www.hwahae.co.kr/)
- [StubbyPlanner](https://www.stubbyplanner.com/planner/planner_rt.asp?lang=)

---

## 📌 **주요 기능**

### **1. 여행지 탐색 및 검색**
- **카테고리별 분류**: 관광지, 도시, 액티비티 등.
- **위치 기반 탐색**: 도시별 여행지 검색.
- 여행지 상세 정보 제공:
  - 사진 및 설명
  - 운영 시간, 주요 명소, 주소
  - 추천 음식점

### **2. 여행지 리뷰 및 댓글**
- **리뷰 작성**: 
  - 별점(1~5점)과 텍스트 리뷰 작성.
  - 사진 첨부(최대 3장).
- **댓글 기능**: (미정)
  - 대댓글(스레드형) 구조 가능.

### **3. 개인화된 기능**
- **위시리스트**: 관심 여행지 저장.
- **여행 기록 관리**: 방문한 여행지 및 리뷰를 프로필에서 확인.
- **맞춤 여행지 추천**: 사용자의 선호 테마, 좋아요 데이터를 분석하여 추천.

### **4. 관리자 기능**
- 여행지 데이터 관리(추가 및 수정).
- 부적절한 리뷰 및 댓글 관리.
- 사용자 관리 시스템(제재 기능 포함).

### **5. 외부 API 활용**
- Google Places API 또는 TripAdvisor API를 이용해 여행지 초기 데이터 세팅.

---

## 🛠 **기술 스택 및 아키텍처**

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
- **추가 요소**: 여행지 데이터와 리뷰, 댓글을 관계형 구조로 연결

---

## 🖥 **주요 UI/UX 흐름**

1. **홈 화면**
   - 인기 여행지 리스트, 최근 리뷰 표시.
   - 검색 및 카테고리별 탐색 버튼 제공.

2. **여행지 상세 페이지**
   - 상단: 여행지 정보(사진, 설명, 주소 등).
   - 중단: 리뷰 리스트.
   - 하단: 댓글 섹션(옵션).

3. **리뷰 작성/수정**
   - 별점 및 텍스트 작성, 사진 업로드 기능 제공.

4. **사용자 프로필**
   - 내 리뷰, 내 여행 스타일, 위시리스트 표시.

---

## 🚀 **기대 효과**

1. **편리한 정보 탐색**: 이미 등록된 여행지 정보를 쉽게 탐색.
2. **사용자 경험 강화**: 리뷰 및 댓글을 통한 소통.
3. **확장 가능성**: 데이터가 쌓일수록 플랫폼 유용성 증가.

---

## 📋 **추가 사항**

- 구글 리뷰 평점과 앱 사용자 리뷰 평점을 별도로 표시하여 비교 가능하도록 설정.
