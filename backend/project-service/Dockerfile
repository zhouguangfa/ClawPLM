# Multi-stage build for project-service
FROM maven:3.8.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy parent pom and the specific module
COPY pom.xml .
COPY project-service/pom.xml ./project-service/pom.xml
COPY project-service/src ./project-service/src
COPY api-gateway/pom.xml ./api-gateway/pom.xml 2>/dev/null || echo "Skipping optional module"
COPY pdm-service/pom.xml ./pdm-service/pom.xml 2>/dev/null || echo "Skipping optional module"
COPY workflow-service/pom.xml ./workflow-service/pom.xml 2>/dev/null || echo "Skipping optional module"

# Build only the project-service module
RUN mvn clean package -pl project-service -am -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/project-service/target/plm-project-service.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]