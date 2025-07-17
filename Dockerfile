# Build stage
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app
COPY . .
RUN ./gradlew build

# Run stage
FROM eclipse-temurin:21-jdk-alpine AS runner

WORKDIR /app
COPY --from=builder /app/build/libs/escape-the-endpoint-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]