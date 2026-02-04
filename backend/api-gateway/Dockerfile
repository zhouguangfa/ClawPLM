# Multi-stage build for api-gateway service
FROM maven:3.8.6-temurin-17-focal AS builder

WORKDIR /app

# Copy parent pom and the specific module
COPY pom.xml .
COPY api-gateway/pom.xml ./api-gateway/pom.xml
COPY api-gateway/src ./api-gateway/src

# Runtime stage
FROM openjdk:17-oraclelinux8
WORKDIR /app
COPY --from=builder /app/api-gateway/target/plm-api-gateway.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]