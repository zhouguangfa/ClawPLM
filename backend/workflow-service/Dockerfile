# Simple runtime image for workflow-service
FROM eclipse-temurin:21-alpine

WORKDIR /app

# Copy pre-built jar file
COPY target/plm-workflow-service.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]