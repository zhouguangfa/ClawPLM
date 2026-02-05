#!/bin/bash

# Build script for ClawPLM services

echo "Building ClawPLM services..."

cd backend

# Build each service individually
echo "Building API Gateway..."
cd api-gateway
if [ ! -f target/plm-api-gateway.jar ]; then
  mvn clean package -DskipTests
fi
cd ..

echo "Building PDM Service..."
cd pdm-service
if [ ! -f target/plm-pdm-service.jar ]; then
  mvn clean package -DskipTests
fi
cd ..

echo "Building Project Service..."
cd project-service
if [ ! -f target/plm-project-service.jar ]; then
  mvn clean package -DskipTests
fi
cd ..

echo "Building Workflow Service..."
cd workflow-service
if [ ! -f target/plm-workflow-service.jar ]; then
  mvn clean package -DskipTests
fi
cd ..

echo "All services built successfully!"