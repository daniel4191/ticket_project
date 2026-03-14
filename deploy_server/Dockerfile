# 애플 실리콘(M1~M4)과 완벽 호환되는 우분투(Jammy) 기반 Java 17 공식 이미지
FROM eclipse-temurin:17-jdk-jammy

# 빌드된 jar 파일을 컨테이너 안으로 복사
COPY build/libs/*.jar app.jar

# 서버 실행 명령어
ENTRYPOINT ["java", "-jar", "/app.jar"]