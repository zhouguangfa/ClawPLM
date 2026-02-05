# Multi-stage build for workflow-service
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder

WORKDIR /app

# Copy the parent pom and the specific module
COPY ../pom.xml .
COPY pom.xml .
COPY src ./src

# Create settings.xml for faster dependency downloads
COPY settings.xml /root/.m2/settings.xml

# Build the application
RUN mvn clean package -DskipTests -Dmaven.test.skip=true -e

# Runtime stage
FROM eclipse-temurin:21-alpine
WORKDIR /app
COPY --from=builder /app/target/plm-workflow-service.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]