# 🎫 대규모 트래픽 처리를 위한 선착순 티켓 예매 시스템

동시에 수많은 사용자가 몰리는 티켓팅 상황을 가정하여, **데이터 정합성 보장**과 **서버 부하 분산**을 목표로 개발한 REST API 서버입니다.

## 🛠 기술 스택 (Tech Stack)
* **Backend:** Java 17, Spring Boot, Spring Data JPA
* **Database:** MySQL 8.0 (RDBMS), Redis (In-Memory Queue)
* **Infrastructure:** Docker, Docker-Compose, Mac Mini (Home Server)
* **Docs:** Swagger UI (OpenAPI)

## 💡 핵심 문제 해결 (Key Features)

### 1. 동시성 이슈(Race Condition) 해결
* **문제:** 100명의 사용자가 동시에 예약을 시도할 때, DB 갱신 손실(Lost Update)로 인해 초과 예약이 발생하는 현상 확인.
* **해결:** Spring Data JPA의 `@Lock(LockModeType.PESSIMISTIC_WRITE)`을 활용하여 DB 레코드에 **비관적 락(Pessimistic Lock)**을 적용. 100%의 데이터 정합성을 보장하도록 개선.

### 2. Redis 대기열(Queue) 시스템 구축
* **문제:** 트래픽이 몰릴 경우 RDBMS(MySQL)의 커넥션 병목 현상 및 서버 다운 위험성 존재.
* **해결:** In-Memory DB인 **Redis의 Sorted Set**을 활용하여 진입 대기열(Queue) 시스템 구현. 사용자에게 실시간 대기 순번을 발급하여 서버 부하를 안전하게 분산.

### 3. 인프라 자동화 및 배포
* `Dockerfile`과 `docker-compose.yaml`을 작성하여 Java Application, MySQL, Redis를 하나의 컨테이너 환경으로 통합.
* Mac Mini를 활용한 홈서버 환경을 구축하고, 포트포워딩을 통해 외부 접속 환경 구성.

## 🚀 실행 및 테스트 방법 (Usage Guide)

### 1. 프로젝트 실행
(http://1.247.87.56:9090/swagger-ui/index.html)
