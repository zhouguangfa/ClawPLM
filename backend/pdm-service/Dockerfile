# Multi-stage build for pdm-service
FROM maven:3.8.4-openjdk-17 AS builder

WORKDIR /app

# Copy settings.xml for faster dependency downloads
COPY ../settings.xml /root/.m2/settings.xml

# Copy the entire backend to ensure parent pom and all dependencies are available
COPY .. .

# Build only the pdm-service module
RUN mvn clean package -pl pdm-service -am -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/pdm-service/target/plm-pdm-service.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]