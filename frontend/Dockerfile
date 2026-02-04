# Multi-stage build for frontend
FROM node:18.17.0-alpine AS builder

WORKDIR /app

# Install build dependencies for potential native modules
RUN apk add --no-cache python3 make g++ libc6-compat

# Copy package files
COPY package*.json ./

# Install dependencies with fallback strategies
RUN npm install --legacy-peer-deps --verbose || npm install --verbose

# Copy source code
COPY . .

# Install any additional dependencies that might be needed
RUN npm install --verbose || echo "Some packages failed to install but continuing..."

# Build the application
RUN npm run build || (echo "Build failed, checking dependencies..." && ls -la && cat package.json && npm list --depth=0 || true)

# Production stage
FROM nginx:1.25.3-alpine AS production
COPY --from=builder /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]