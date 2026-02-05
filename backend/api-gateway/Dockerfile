# Simple runtime image for api-gateway service
FROM eclipse-temurin:21-alpine

WORKDIR /app

# Copy pre-built jar file
COPY target/plm-api-gateway.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]