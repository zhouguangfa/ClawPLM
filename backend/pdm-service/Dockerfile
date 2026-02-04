# Multi-stage build for pdm-service
FROM maven:3.8.4-openjdk-17 AS builder

WORKDIR /app

# Copy parent pom and the specific module
COPY pom.xml .
COPY pdm-service/pom.xml ./pdm-service/pom.xml
COPY pdm-service/src ./pdm-service/src

# Build only the pdm-service module
RUN mvn clean package -pl pdm-service -am -DskipTests

# Runtime stage
FROM openjdk:17-jdk
WORKDIR /app
COPY --from=builder /app/pdm-service/target/plm-pdm-service.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]