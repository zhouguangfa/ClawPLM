# Multi-stage build for frontend
FROM node:18 AS builder

WORKDIR /app

# Install build dependencies for potential native modules
RUN apt-get update && apt-get install -y python3 make g++ && rm -rf /var/lib/apt/lists/*

# Copy package files
COPY package*.json ./

# Install dependencies with fallback strategies
RUN npm ci --legacy-peer-deps --verbose || npm install --legacy-peer-deps --verbose

# Copy source code
COPY . .

# Use production vite config for build
RUN if [ -f "vite.config.prod.js" ]; then cp vite.config.prod.js vite.config.js; fi

# Build the application with detailed logging
RUN npm run build --verbose || (echo "Build failed, listing files for debugging:" && ls -la && ls -la src/ && cat vite.config.js && npm run build)

# Production stage
FROM nginx:stable
COPY --from=builder /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]