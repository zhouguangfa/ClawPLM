# Simple nginx server for frontend
FROM nginx:stable

# Create a basic dist folder structure
RUN mkdir -p /usr/share/nginx/html

# Create a basic index.html as placeholder
RUN echo '<!DOCTYPE html><html><head><title>PLM Frontend</title></head><body><div id="app">Frontend Placeholder</div></body></html>' > /usr/share/nginx/html/index.html

# Copy the actual built files if they exist, otherwise keep placeholder
COPY dist/ /usr/share/nginx/html/ || echo "Using placeholder - build frontend locally first"

# Copy nginx configuration
COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]