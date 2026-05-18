# Despachos Backend - Innovatech Chile
API REST con Spring Boot 3.4.4 y Java 17.
Ejecutar localmente: docker compose up --build
Endpoints: GET /api/v1/despachos y GET /api/v1/ventas
Pipeline CI/CD: push a rama deploy activa build, push ECR y deploy en EC2 via SSM
