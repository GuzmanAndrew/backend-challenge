FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

COPY src src

RUN chmod +x ./gradlew

RUN ./gradlew clean bootJar
RUN ls -la build/libs/

ENTRYPOINT ["java", "-jar", "/app/build/libs/backend-challenge-0.0.1.jar"]