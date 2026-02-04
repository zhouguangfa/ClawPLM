# Multi-stage build for frontend
FROM node:18.17.0-alpine AS builder

WORKDIR /app

# Install build dependencies for potential native modules
RUN apk add --no-cache python3 make g++ libc6-compat

# Copy package files
COPY package*.json ./

# Create a basic package-lock.json if it doesn't exist to satisfy npm ci
RUN npm install --package-lock-only --ignore-scripts --verbose || true

# Install dependencies with fallback strategies
RUN npm ci --legacy-peer-deps --verbose || npm install --legacy-peer-deps --verbose

# Copy source code
COPY . .

# Build the application
RUN npm run build

# Production stage
FROM nginx:1.25.3-alpine AS production
COPY --from=builder /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]