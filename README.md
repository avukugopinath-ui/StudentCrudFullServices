# Student CRUD Microservice (Spring Boot)

A simple Spring Boot microservice for Student CRUD operations with:
- PostgreSQL database
- Kafka event publishing/consuming
- Docker and Docker Compose support
- AWS-ready deployment artifacts (ECS/Fargate)
- Maven project structure compatible with STS import

## Project Structure

```text
MyStudentApp/
├── pom.xml
├── Dockerfile
├── docker-compose.yml
├── aws/
│   └── ecs-task-definition.json
└── src/
    └── main/
        ├── java/com/example/studentcrud/
        │   ├── controller/
        │   ├── service/
        │   ├── repository/
        │   ├── entity/
        │   ├── kafka/
        │   └── exception/
        └── resources/
            ├── application.yml
            └── db/migration/V1__create_students_table.sql
```

## Import into STS

1. Open STS.
2. Go to File -> Import -> Maven -> Existing Maven Projects.
3. Select the `MyStudentApp` folder.
4. Click Finish.

## Run Locally (Docker)

```bash
docker compose up --build
```

Service URL: `http://localhost:8080`

### Run with profile-specific compose files

Local:

```bash
docker compose -f docker-compose.yml -f docker-compose.local.yml up --build
```

SIT:

```bash
docker compose -f docker-compose.yml -f docker-compose.sit.yml up --build
```

Prod:

```bash
docker compose -f docker-compose.yml -f docker-compose.prod.yml up --build
```

Note: for `sit` and `prod`, set environment variables (DB/Kafka endpoints and credentials) before starting containers.

## API Endpoints

- `GET /api/students` - List students
- `GET /api/students/{id}` - Get student by ID
- `POST /api/students` - Create student
- `PUT /api/students/{id}` - Update student
- `DELETE /api/students/{id}` - Delete student

Sample create payload:

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com"
}
```

## AWS Deployment (ECS/Fargate)

1. Build and push Docker image to ECR.
2. Create or use an RDS PostgreSQL instance.
3. Create or use Amazon MSK cluster for Kafka.
4. Update placeholders in `aws/ecs-task-definition.json`.
5. Register task definition:

```bash
aws ecs register-task-definition --cli-input-json file://aws/ecs-task-definition.json
```

6. Create or update the ECS service in your target cluster.

## Notes

- Flyway creates the `students` table automatically at startup.
- CRUD actions publish Kafka events to `student-events` topic.

