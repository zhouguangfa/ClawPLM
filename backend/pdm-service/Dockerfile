# Multi-stage build for pdm-service
FROM maven:3.8.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy parent pom and the specific module
COPY pom.xml .
COPY pdm-service/pom.xml ./pdm-service/pom.xml
COPY pdm-service/src ./pdm-service/src
COPY api-gateway/pom.xml ./api-gateway/pom.xml 2>/dev/null || echo "Skipping optional module"
COPY project-service/pom.xml ./project-service/pom.xml 2>/dev/null || echo "Skipping optional module"
COPY workflow-service/pom.xml ./workflow-service/pom.xml 2>/dev/null || echo "Skipping optional module"

# Build only the pdm-service module
RUN mvn clean package -pl pdm-service -am -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/pdm-service/target/plm-pdm-service.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]