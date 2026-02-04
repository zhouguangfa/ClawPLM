# Multi-stage build for project-service
FROM maven:3.8.4-openjdk-17 AS builder

WORKDIR /app

# Copy settings.xml for faster dependency downloads
COPY ../settings.xml /root/.m2/settings.xml

# Copy the parent pom and the specific module
COPY ../pom.xml .
COPY pom.xml .
COPY src ./src

# Build the project-service module
RUN mvn clean package -DskipTests -B

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/plm-project-service.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]