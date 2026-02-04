# Multi-stage build for PLM backend services
FROM maven:3.8.4-openjdk-17 AS builder

WORKDIR /app

# Copy settings.xml for faster dependency downloads
COPY settings.xml /root/.m2/settings.xml

# Copy the essential files for Maven to work
COPY pom.xml .
COPY api-gateway/pom.xml ./api-gateway/pom.xml
COPY pdm-service/pom.xml ./pdm-service/pom.xml
COPY project-service/pom.xml ./project-service/pom.xml
COPY workflow-service/pom.xml ./workflow-service/pom.xml

# Download dependencies first to leverage Docker caching
RUN mvn dependency:go-offline -B

# Now copy the source code
COPY . .

# Build specific service based on BUILD_SERVICE argument
ARG BUILD_SERVICE=api-gateway
RUN if [ "${BUILD_SERVICE}" = "api-gateway" ]; then \
      mvn clean package -pl api-gateway -am -DskipTests -B; \
    elif [ "${BUILD_SERVICE}" = "pdm-service" ]; then \
      mvn clean package -pl pdm-service -am -DskipTests -B; \
    elif [ "${BUILD_SERVICE}" = "project-service" ]; then \
      mvn clean package -pl project-service -am -DskipTests -B; \
    elif [ "${BUILD_SERVICE}" = "workflow-service" ]; then \
      mvn clean package -pl workflow-service -am -DskipTests -B; \
    else \
      mvn clean package -pl api-gateway -am -DskipTests -B; \
    fi

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the appropriate jar file based on service name
ARG BUILD_SERVICE=api-gateway
COPY --from=builder /app/${BUILD_SERVICE}/target/plm-${BUILD_SERVICE}.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]