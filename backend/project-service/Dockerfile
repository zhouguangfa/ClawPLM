# Multi-stage build for project-service
FROM maven:3.8.4-openjdk-17 AS builder

WORKDIR /app

# Copy parent pom and the specific module
COPY ../pom.xml .
COPY pom.xml .
COPY src ./src

# Build only the project-service module
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/plm-project-service.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]