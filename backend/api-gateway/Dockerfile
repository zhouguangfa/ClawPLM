# Multi-stage build for api-gateway service
FROM maven:3.8.4-openjdk-17 AS builder

WORKDIR /app

# Copy settings.xml for faster dependency downloads
COPY ../settings.xml /root/.m2/settings.xml

# Copy the entire backend to ensure parent pom and all dependencies are available
COPY .. .

# Build only the api-gateway module
RUN mvn clean package -pl api-gateway -am -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/api-gateway/target/plm-api-gateway.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]