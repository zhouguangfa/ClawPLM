# Multi-stage build for workflow-service
FROM maven:3.8.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy parent pom and the specific module
COPY pom.xml .
COPY workflow-service/pom.xml ./workflow-service/pom.xml
COPY workflow-service/src ./workflow-service/src
COPY api-gateway/pom.xml ./api-gateway/pom.xml 2>/dev/null || echo "Skipping optional module"
COPY pdm-service/pom.xml ./pdm-service/pom.xml 2>/dev/null || echo "Skipping optional module"
COPY project-service/pom.xml ./project-service/pom.xml 2>/dev/null || echo "Skipping optional module"

# Build only the workflow-service module
RUN mvn clean package -pl workflow-service -am -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/workflow-service/target/plm-workflow-service.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]