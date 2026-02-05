# Simple runtime image for pdm-service
FROM eclipse-temurin:21-alpine

WORKDIR /app

# Copy pre-built jar file
COPY target/plm-pdm-service.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]