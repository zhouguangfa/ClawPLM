# Multi-stage build for workflow-service
FROM maven:3.8.4-openjdk-17 AS builder

WORKDIR /app

# Copy parent pom and the specific module
COPY pom.xml .
COPY workflow-service/pom.xml ./workflow-service/pom.xml
COPY workflow-service/src ./workflow-service/src

# Build only the workflow-service module
RUN mvn clean package -pl workflow-service -am -DskipTests

# Runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/workflow-service/target/plm-workflow-service.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]