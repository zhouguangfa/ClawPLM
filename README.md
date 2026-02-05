# ClawPLM - Product Lifecycle Management System

A comprehensive Product Lifecycle Management system built with Java Spring Boot, MySQL, Vue.js and Kafka.

## Features
- Product Data Management (PDM)
- Project Management
- Workflow Management
- Collaborative Design
- Supply Chain Integration
- Quality & Compliance Management
- Analytics & Reporting
- Customer Feedback Management

## Tech Stack
- Backend: Java Spring Boot + Spring Cloud
- Database: MySQL
- Frontend: Vue.js
- Message Queue: Apache Kafka
- Containerization: Docker + Kubernetes
- Cache: Redis

## Architecture
The system follows a microservices architecture pattern with the following components:
- API Gateway
- PDM Service
- Project Management Service
- Workflow Service
- Collaboration Service
- Quality Management Service
- Security Module

## Running the Application

### Prerequisites
- Docker and Docker Compose
- Java 21 (for local development)
- Maven 3.9+ (for local development)
- Node.js 18+ (for frontend development)

### Method 1: Full Docker Deployment (Recommended)

1. Clone the repository:
```bash
git clone https://github.com/zhouguangfa/ClawPLM.git
cd ClawPLM
```

2. Build the backend services locally:
```bash
cd backend
mvn clean package -DskipTests
cd ..
```

3. Build the frontend (optional, if making changes):
```bash
cd frontend
npm install
npm run build
cd ..
```

4. Start the entire system:
```bash
docker-compose up --build
```

Access the applications:
- Frontend: http://localhost
- API Gateway: http://localhost:8080
- PDM Service: http://localhost:8081
- Project Service: http://localhost:8082
- Workflow Service: http://localhost:8083

### Method 2: Development Mode

For development, you can run services individually:

1. Start infrastructure services:
   ```bash
   docker-compose up mysql redis kafka
   ```

2. Run backend services directly:
   ```bash
   cd backend/api-gateway
   mvn spring-boot:run
   
   cd ../pdm-service
   mvn spring-boot:run
   
   cd ../project-service
   mvn spring-boot:run
   
   cd ../workflow-service
   mvn spring-boot:run
   ```

3. Run frontend:
   ```bash
   cd frontend
   npm install
   npm run dev
   ```

### Method 3: Using Docker Compose with Pre-built JARs (Alternative)

If you encounter build issues, you can use the pre-built JAR approach:

1. Clone the repository:
```bash
git clone https://github.com/zhouguangfa/ClawPLM.git
cd ClawPLM
```

2. Ensure you have JAR files in place (either by building locally or obtaining pre-built ones):
```bash
# Make sure target directories exist with JAR files
ls -la backend/api-gateway/target/
ls -la backend/pdm-service/target/
ls -la backend/project-service/target/
ls -la backend/workflow-service/target/
```

3. Start services with Docker Compose:
```bash
docker-compose up --build
```

## Configuration

### Environment Variables
The application can be configured using environment variables defined in docker-compose.yml:

#### Backend Services
- `SPRING_PROFILES_ACTIVE`: Profile to use (default: `docker`)
- `SERVER_PORT`: Service port
- `SPRING_DATASOURCE_URL`: Database connection URL
- `SPRING_REDIS_HOST`: Redis host
- `SPRING_KAFKA_BOOTSTRAP_SERVERS`: Kafka servers

#### Frontend
- `API_BASE_URL`: Base URL for API (default: `http://api-gateway:8080`)

## Project Structure
```
ClawPLM/
├── backend/                 # Java Spring Boot microservices
│   ├── api-gateway/         # API Gateway service
│   ├── pdm-service/         # Product Data Management service
│   ├── project-service/     # Project Management service
│   ├── workflow-service/    # Workflow Management service
│   ├── pom.xml              # Parent POM file
│   └── settings.xml         # Maven settings
├── frontend/                # Vue.js frontend application
│   ├── src/                 # Source code
│   ├── package.json         # Dependencies
│   ├── vite.config.js       # Dev configuration
│   ├── vite.config.prod.js  # Production configuration
│   ├── index.html           # Entry HTML file
│   └── nginx.conf           # Nginx configuration
├── docker-compose.yml       # Docker Compose configuration
├── init.sql                 # Database initialization script
└── README.md                # This file
```

## API Endpoints

### Product Management
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product

### Project Management
- `GET /api/projects` - Get all projects
- `GET /api/projects/{id}` - Get project by ID
- `POST /api/projects` - Create new project
- `PUT /api/projects/{id}` - Update project
- `DELETE /api/projects/{id}` - Delete project

## Development

### Adding New Services
To add a new microservice:
1. Create a new module in the `backend/` directory
2. Add it to the parent `pom.xml` modules section
3. Include necessary dependencies (Spring Boot starter, JPA, Kafka, etc.)
4. Configure it in `docker-compose.yml`

### Frontend Components
The frontend includes:
- Dashboard with key metrics
- Product management interface
- Project management interface
- Task management system
- Workflow visualization

## Troubleshooting

### Common Issues
1. **Port conflicts**: Ensure ports 80, 8080-8083, 3306, 6379, 9092 are available
2. **Database connection errors**: Verify MySQL is running and credentials are correct
3. **Service startup delays**: Backend services may take 1-2 minutes to fully start
4. **Maven build issues**: Ensure Java 21 and Maven 3.9+ are installed

### Build Troubleshooting
If you encounter Maven build failures:
1. Check your Java version: `java -version` (should be Java 21)
2. Check your Maven version: `mvn -version` (should be 3.9+)
3. Clear Maven cache: `mvn dependency:purge-local-repository`
4. Ensure all POM files have correct dependencies and versions

### Useful Commands
```bash
# View all running containers
docker-compose ps

# View logs for a specific service
docker-compose logs api-gateway

# Stop all services
docker-compose down

# Clean up everything including volumes
docker-compose down -v

# Force rebuild all services
docker-compose up --build --force-recreate

# View logs in real-time
docker-compose logs -f
```

## Contributing
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License
This project is licensed under the MIT License.