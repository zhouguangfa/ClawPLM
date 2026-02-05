#!/bin/bash

# Script to build frontend assets
set -e  # Exit on any error

echo "Building frontend assets..."

# Change to the frontend directory
cd frontend

# Install dependencies
echo "Installing dependencies..."
npm install

# Build the application
echo "Building application..."
npm run build

echo "Frontend build completed successfully!"
echo "Assets are available in the dist/ folder"