#!/bin/bash

echo "PLM System Deployment Script"

# Check if git is installed
if ! command -v git &> /dev/null; then
    echo "Git is not installed. Please install git first."
    exit 1
fi

# Check if we're in a git repository
if [ ! -d ".git" ]; then
    echo "Initializing git repository..."
    git init
    git add .
    git commit -m "Initial commit: PLM System basic structure"
fi

echo "Git repository is ready."

echo ""
echo "To push this code to GitHub, please follow these steps:"
echo ""
echo "1. Create a new repository on GitHub (https://github.com/new)"
echo "2. Name it 'plm-system' or any name you prefer"
echo "3. Copy the repository URL (e.g., https://github.com/username/plm-system.git)"
echo "4. Run the following commands in your terminal:"
echo ""
echo "   git remote add origin YOUR_REPOSITORY_URL"
echo "   git branch -M main"
echo "   git push -u origin main"
echo ""
echo "Replace 'YOUR_REPOSITORY_URL' with the actual URL of your GitHub repository."
echo ""

echo "Alternatively, if you have GitHub CLI installed, you can run:"
echo ""
echo "   gh repo create plm-system --public --push"
echo ""
echo "This will create a new public repository and push the code automatically."
echo ""

echo ""
echo "Project structure created successfully!"
echo "Backend: Java Spring Boot microservices"
echo "Frontend: Vue.js application"
echo "Database: MySQL schema"
echo "Message Queue: Kafka configuration"
echo "Containerization: Docker and Docker Compose"