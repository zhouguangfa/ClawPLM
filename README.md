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
- Java 17
- Node.js 16+

### Method 1: Using Docker Compose (Recommended)

1. Clone the repository:
```bash
git clone https://github.com/zhouguangfa/ClawPLM.git
cd ClawPLM
```

2. Start all services using Docker Compose:
```bash
docker-compose up -d
```

3. Wait for all containers to start (approximately 2-3 minutes):
```bash
docker-compose logs -f
```

4. Access the applications:
   - Frontend: http://localhost
   - API Gateway: http://localhost:8080
   - MySQL: localhost:3306
   - Redis: localhost:6379
   - Kafka: localhost:9092

### Method 2: Running Services Individually

#### 1. Database Setup
First, ensure MySQL is running and create the database:
```bash
# Start MySQL container
docker run -d --name mysql-plm -e MYSQL_ROOT_PASSWORD=rootpassword -e MYSQL_DATABASE=plm_db -p 3306:3306 mysql:8.0

# Execute the init.sql script
docker cp init.sql mysql-plm:/tmp/init.sql
docker exec -it mysql-plm mysql -u root -prootpassword plm_db -e "SOURCE /tmp/init.sql;"
```

#### 2. Start Supporting Services
```bash
# Start Redis
docker run -d --name redis-plm -p 6379:6379 redis:7-alpine

# Start Kafka (with Zookeeper)
docker run -d --name zookeeper -e ZOOKEEPER_CLIENT_PORT=2181 -e ZOOKEEPER_TICK_TIME=2000 confluentinc/cp-zookeeper:latest
docker run -d --name kafka -e KAFKA_BROKER_ID=1 -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 -p 9092:9092 --link zookeeper confluentinc/cp-kafka:latest
```

#### 3. Build and Run Backend Services
```bash
# Navigate to backend directory
cd backend

# Build the project
mvn clean install

# Run services individually:
# Terminal 1: Start API Gateway
cd api-gateway
../mvnw spring-boot:run

# Terminal 2: Start PDM Service
cd ../pdm-service
../mvnw spring-boot:run

# Terminal 3: Start Project Service
cd ../project-service
../mvnw spring-boot:run

# Terminal 4: Start Workflow Service
cd ../workflow-service
../mvnw spring-boot:run
```

#### 4. Run Frontend
```bash
# Navigate to frontend directory
cd frontend

# Install dependencies
npm install

# Start development server
npm run dev
```

### Method 3: Using Maven and NPM (Development Mode)

1. Install dependencies:
```bash
cd backend
mvn clean install

cd ../frontend
npm install
```

2. Start all services:
```bash
# Terminal 1: Start supporting services
docker-compose up mysql redis kafka zookeeper

# Terminal 2: Start backend services
cd backend/api-gateway
../mvnw spring-boot:run

# Terminal 3: Start PDM service
cd backend/pdm-service
../mvnw spring-boot:run

# Terminal 4: Start Project service
cd backend/project-service
../mvnw spring-boot:run

# Terminal 5: Start Workflow service
cd backend/workflow-service
../mvnw spring-boot:run

# Terminal 6: Start frontend
cd frontend
npm run dev
```

## Configuration

### Environment Variables
The application can be configured using environment variables:

#### Backend Services
- `SPRING_PROFILES_ACTIVE`: Profile to use (default: `dev`)
- `MYSQL_HOST`: MySQL host (default: `localhost`)
- `MYSQL_PORT`: MySQL port (default: `3306`)
- `MYSQL_DATABASE`: Database name (default: `plm_db`)
- `MYSQL_USER`: Database user (default: `plm_user`)
- `MYSQL_PASSWORD`: Database password (default: `plm_password`)
- `REDIS_HOST`: Redis host (default: `localhost`)
- `REDIS_PORT`: Redis port (default: `6379`)
- `KAFKA_BOOTSTRAP_SERVERS`: Kafka servers (default: `localhost:9092`)

#### Frontend
- `VUE_APP_API_BASE_URL`: Base URL for API (default: `http://localhost:8080`)

## Project Structure
```
ClawPLM/
├── backend/                 # Java Spring Boot microservices
│   ├── api-gateway/         # API Gateway service
│   ├── pdm-service/         # Product Data Management service
│   ├── project-service/     # Project Management service
│   ├── workflow-service/    # Workflow Management service
│   └── pom.xml              # Parent POM file
├── frontend/                # Vue.js frontend application
├── docker-compose.yml       # Docker Compose configuration
├── Dockerfile               # Docker configuration for backend
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
3. **Service discovery issues**: Ensure all services are registered with Eureka

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
```

## Contributing
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License
This project is licensed under the MIT License.