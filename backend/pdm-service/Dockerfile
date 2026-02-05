# Multi-stage build for pdm-service
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder

WORKDIR /app

# Copy the parent pom and the specific module
COPY ../pom.xml .
COPY pom.xml .
COPY src ./src

# Create settings.xml for faster dependency downloads
RUN mkdir -p /root/.m2 && \
    echo '<?xml version="1.0" encoding="UTF-8"?>' > /root/.m2/settings.xml && \
    echo '<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"' >> /root/.m2/settings.xml && \
    echo '          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"' >> /root/.m2/settings.xml && \
    echo '          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">' >> /root/.m2/settings.xml && \
    echo '  <mirrors>' >> /root/.m2/settings.xml && \
    echo '    <mirror>' >> /root/.m2/settings.xml && \
    echo '      <id>aliyunmaven</id>' >> /root/.m2/settings.xml && \
    echo '      <mirrorOf>*</mirrorOf>' >> /root/.m2/settings.xml && \
    echo '      <name>aliyun mirror</name>' >> /root/.m2/settings.xml && \
    echo '      <url>https://maven.aliyun.com/repository/public</url>' >> /root/.m2/settings.xml && \
    echo '    </mirror>' >> /root/.m2/settings.xml && \
    echo '  </mirrors>' >> /root/.m2/settings.xml && \
    echo '</settings>' >> /root/.m2/settings.xml

# Set environment variables for Maven
ENV MAVEN_OPTS="-Xmx2048m -Dmaven.repo.local=/root/.m2/repository"

# Build the application
RUN mvn clean package -DskipTests -e

# Runtime stage
FROM eclipse-temurin:21-alpine
WORKDIR /app
COPY --from=builder /app/target/plm-pdm-service.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]