# Multi-stage build for PLM backend services
FROM maven:3.8.4-openjdk-17 AS builder

WORKDIR /app

# Copy settings.xml for faster dependency downloads
COPY settings.xml /root/.m2/settings.xml

# Copy the entire backend to ensure parent pom and all dependencies are available
COPY . .

# Build specific service based on BUILD_SERVICE argument
ARG BUILD_SERVICE=api-gateway
RUN case ${BUILD_SERVICE} in \
      "api-gateway") \
        mvn clean package -pl api-gateway -am -DskipTests ;; \
      "pdm-service") \
        mvn clean package -pl pdm-service -am -DskipTests ;; \
      "project-service") \
        mvn clean package -pl project-service -am -DskipTests ;; \
      "workflow-service") \
        mvn clean package -pl workflow-service -am -DskipTests ;; \
      *) \
        mvn clean package -pl api-gateway -am -DskipTests ;; \
    esac

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the appropriate jar file based on service name
ARG BUILD_SERVICE=api-gateway
COPY --from=builder /app/${BUILD_SERVICE}/target/plm-${BUILD_SERVICE}.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]