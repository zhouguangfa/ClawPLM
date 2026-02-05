# Multi-stage build for frontend
FROM node:18 AS builder

WORKDIR /app

# Install build dependencies for potential native modules
RUN apt-get update && apt-get install -y python3 make g++ && rm -rf /var/lib/apt/lists/*

# Copy package files
COPY package*.json ./

# Install dependencies with fallback strategies
RUN npm install --verbose || npm install --legacy-peer-deps --verbose

# Copy source code
COPY . .

# Check if production config exists and use it
RUN if [ -f "vite.config.prod.js" ]; then cp vite.config.prod.js vite.config.js; fi

# List files for debugging
RUN ls -la && ls -la src/ && cat vite.config.js

# Try to run a simple node command to test environment
RUN node -e "console.log('Node environment OK'); console.log(process.version);" 

# Install any additional dependencies that might be needed
RUN npm install vite vue@^3 --save-dev --verbose

# Build the application with detailed logging
RUN npm run build

# Production stage
FROM nginx:stable
COPY --from=builder /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]