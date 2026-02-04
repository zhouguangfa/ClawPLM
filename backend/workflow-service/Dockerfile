# Multi-stage build for workflow-service
FROM maven:3.8.4-openjdk-17 AS builder

WORKDIR /app

# Copy the entire backend to ensure parent pom and all dependencies are available
COPY .. .

# Build only the workflow-service module
RUN mvn clean package -pl workflow-service -am -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/workflow-service/target/plm-workflow-service.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]