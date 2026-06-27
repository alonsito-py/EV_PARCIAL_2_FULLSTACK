# ─── Stage 1: Build ────────────────────────────────────────────
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -q
COPY src ./src
RUN mvn package -DskipTests -q

# ─── Stage 2: Runtime ──────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN addgroup -S urbano && adduser -S urbano -G urbano
COPY --from=builder /app/target/*.jar app.jar
RUN chown urbano:urbano app.jar
USER urbano
EXPOSE 8081
ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILE:-dev}", "-jar", "app.jar"]
