# Multi-stage build for frontend
FROM node:18-alpine AS builder

WORKDIR /app

# Install build dependencies for potential native modules
RUN apk add --no-cache python3 make g++ libc6-compat

# Copy package files
COPY package*.json ./

# Install dependencies with additional flags to handle potential issues
RUN npm install --legacy-peer-deps --verbose || npm install --verbose

# Copy source code
COPY . .

# Build the application
RUN npm run build

# Production stage
FROM nginx:alpine AS production
COPY --from=builder /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]