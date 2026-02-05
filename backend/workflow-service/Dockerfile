# Multi-stage build for workflow-service
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

# Print information about the build environment
RUN echo "=== Build Environment Information ===" && \
    java -version && \
    mvn -version && \
    echo "=== Working Directory Contents ===" && \
    ls -la && \
    echo "=== Source Directory Contents ===" && \
    ls -la src && \
    echo "=== POM Content ===" && \
    cat pom.xml

# Install parent POM locally to ensure it's available
RUN mvn install:install-file -Dfile=../pom.xml -DgroupId=com.plm -DartifactId=plm-parent -Dversion=1.0.0 -Dpackaging=pom || echo "Parent POM installation skipped"

# Build the application with verbose logging to diagnose issues
RUN mvn clean compile -X || \
    mvn clean compile -Dmaven.test.skip=true -e || \
    mvn clean package -DskipTests -e

# Runtime stage
FROM eclipse-temurin:21-alpine
WORKDIR /app
COPY --from=builder /app/target/plm-workflow-service.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]