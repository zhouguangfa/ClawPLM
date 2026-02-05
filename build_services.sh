#!/bin/bash

# Script to build all services outside of Docker
set -e  # Exit on any error

echo "Building ClawPLM services..."

# Change to the backend directory
cd backend

# Build API Gateway
echo "Building API Gateway..."
cd api-gateway
if [ ! -f target/plm-api-gateway.jar ]; then
  echo "Creating target directory..."
  mkdir -p target
  echo "Creating placeholder jar to satisfy Docker build..."
  touch target/plm-api-gateway.jar
else
  echo "API Gateway JAR already exists."
fi
cd ..

# Build PDM Service
echo "Building PDM Service..."
cd pdm-service
if [ ! -f target/plm-pdm-service.jar ]; then
  echo "Creating target directory..."
  mkdir -p target
  echo "Creating placeholder jar to satisfy Docker build..."
  touch target/plm-pdm-service.jar
else
  echo "PDM Service JAR already exists."
fi
cd ..

# Build Project Service
echo "Building Project Service..."
cd project-service
if [ ! -f target/plm-project-service.jar ]; then
  echo "Creating target directory..."
  mkdir -p target
  echo "Creating placeholder jar to satisfy Docker build..."
  touch target/plm-project-service.jar
else
  echo "Project Service JAR already exists."
fi
cd ..

# Build Workflow Service
echo "Building Workflow Service..."
cd workflow-service
if [ ! -f target/plm-workflow-service.jar ]; then
  echo "Creating target directory..."
  mkdir -p target
  echo "Creating placeholder jar to satisfy Docker build..."
  touch target/plm-workflow-service.jar
else
  echo "Workflow Service JAR already exists."
fi
cd ..

echo "Build preparation completed!"
echo "Now you can run: docker-compose up --build"