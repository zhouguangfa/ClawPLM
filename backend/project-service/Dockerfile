# Multi-stage build for project-service
FROM maven:3.8.4-openjdk-17 AS builder

WORKDIR /app

# Copy parent pom and the specific module
COPY pom.xml .
COPY project-service/pom.xml ./project-service/pom.xml
COPY project-service/src ./project-service/src

# Build only the project-service module
RUN mvn clean package -pl project-service -am -DskipTests

# Runtime stage
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=builder /app/project-service/target/plm-project-service.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]