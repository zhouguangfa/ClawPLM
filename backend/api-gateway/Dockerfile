# Multi-stage build for api-gateway service
FROM maven:3.8.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy parent pom and the specific module
COPY pom.xml .
COPY api-gateway/pom.xml ./api-gateway/pom.xml
COPY api-gateway/src ./api-gateway/src
COPY pdm-service/pom.xml ./pdm-service/pom.xml 2>/dev/null || echo "Skipping optional module"
COPY project-service/pom.xml ./project-service/pom.xml 2>/dev/null || echo "Skipping optional module"
COPY workflow-service/pom.xml ./workflow-service/pom.xml 2>/dev/null || echo "Skipping optional module"

# Build only the api-gateway module
RUN mvn clean package -pl api-gateway -am -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/api-gateway/target/plm-api-gateway.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]