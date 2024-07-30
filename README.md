## (간단한)실시간 채팅 시스템
- 이 시스템은 사용자가 회원가입 및 로그인을 통해 다른 유저를 선택하여 실시간 채팅을 진행 할 수 있는 기능을 제공합니다.

## 도메인 설계
- **Member**: 사용자 정보 관리
- **Role**: 권한 관리 관리
- **ChatMessage**: 채팅 메세지 관리
- **ChatRoom**: 채팅 방 관리
- **ChatRoomMember**: 채팅 방 멤버 관리
- 시스템은 사용자가 로그인 하면 유저 목록 조회, 실시간 채팅을 할 수 있습니다.

## 활용 기술
- **Spring Boot** 3.2.7 : 웹 애플리케이션 개발을 위한 기본 프레임워크
- **Java 17** : Spring Boot 3.x.x에 대응하는 Java 버전을 사용
- **Spring Data JPA** : 데이터베이스 객체 관리
- **Querydsl** : 타입 안전 쿼리 SQL 쿼리 작성
- **MySQL** : 데이터베이스 서버
- **Spring Security** : 인증 및 인가 처리
- **JWT(JSON Web Tokens)** : 안전한 사용자 인증을 위한 토큰 기반 인증 시스템
- **Thymeleaf** : 서버사이드 템플릿 엔진으로, HTML을 생성하여 동적으로 뷰를 렌더링

## ERD
![chatApplication](https://github.com/user-attachments/assets/27201400-2b95-4f24-bc82-5d0f195bf5ef)

## 주요화면
- 로그인 화면 페이지
  - ![login](https://github.com/user-attachments/assets/4d89ebd5-c9ec-40b3-9ee9-a000da3e469f)
- 회원 가입 페이지
  - ![register](https://github.com/user-attachments/assets/83c7424c-c37a-4299-b60e-428ae6023354)
- 유저 리스트 페이지
  - ![userlist](https://github.com/user-attachments/assets/bc663163-71e9-4189-9a11-583238ffde7d)
- 채팅방 페이지
  - ![chatroom](https://github.com/user-attachments/assets/28b2399c-0fb4-40a4-bca1-8bd27f8387dd)

