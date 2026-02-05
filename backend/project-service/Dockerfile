# Simple runtime image for project-service
FROM eclipse-temurin:21-alpine

WORKDIR /app

# Copy pre-built jar file
COPY target/plm-project-service.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]