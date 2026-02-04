# Multi-stage build for PLM backend services
FROM maven:3.8.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy the parent pom and all modules
COPY pom.xml .
COPY api-gateway ./api-gateway
COPY pdm-service ./pdm-service
COPY project-service ./project-service
COPY workflow-service ./workflow-service

# Build specific service based on SERVICE_NAME argument
ARG SERVICE_NAME=api-gateway
RUN if [ "$SERVICE_NAME" = "api-gateway" ] ; then \
      mvn clean package -pl api-gateway -am -DskipTests ; \
    elif [ "$SERVICE_NAME" = "pdm-service" ] ; then \
      mvn clean package -pl pdm-service -am -DskipTests ; \
    elif [ "$SERVICE_NAME" = "project-service" ] ; then \
      mvn clean package -pl project-service -am -DskipTests ; \
    elif [ "$SERVICE_NAME" = "workflow-service" ] ; then \
      mvn clean package -pl workflow-service -am -DskipTests ; \
    else \
      mvn clean package -pl api-gateway -am -DskipTests ; \
    fi

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the appropriate jar file based on service name
ARG SERVICE_NAME=api-gateway
COPY --from=builder /app/$SERVICE_NAME/target/plm-$SERVICE_NAME.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]