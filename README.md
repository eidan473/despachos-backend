# Backend Despachos — Springboot API REST

API REST construida con Spring Boot 3.4.4 y Java 17 para la gestión de despachos de Innovatech Chile.

## Tecnologías
- Java 17
- Spring Boot 3.4.4
- MySQL 8.0
- Docker (multi-stage build)
- GitHub Actions + Amazon ECR + AWS SSM

## Endpoints disponibles

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | /api/v1/despachos | Listar todos los despachos |
| GET | /api/v1/despachos/{id} | Obtener despacho por ID |
| POST | /api/v1/despachos | Crear nuevo despacho |
| PUT | /api/v1/despachos/{id} | Actualizar despacho |
| DELETE | /api/v1/despachos/{id} | Eliminar despacho |

Documentación Swagger disponible en: `http://localhost:8081/swagger-ui.html`

## Variables de entorno requeridas

| Variable | Descripción | Ejemplo |
|----------|-------------|---------|
| DB_ENDPOINT | Host de la base de datos | db (compose) / IP privada EC2 |
| DB_PORT | Puerto MySQL | 3306 |
| DB_NAME | Nombre de la base de datos | despachos_db |
| DB_USERNAME | Usuario MySQL | despacho_user |
| DB_PASSWORD | Contraseña MySQL | tu_clave_segura |

## Levantar localmente con Docker Compose

```bash
# 1. Copiar y completar variables de entorno
cp .env.example .env

# 2. Construir y levantar los servicios
docker compose up --build -d

# 3. Verificar que estén corriendo
docker compose ps

# 4. Probar la API
curl http://localhost:8081/api/v1/despachos
```

## Estructura del Dockerfile (multi-stage)

- **Stage 1 (builder):** usa `maven:3.9.6-eclipse-temurin-17-alpine` para compilar el `.jar`
- **Stage 2 (runtime):** usa `eclipse-temurin:17-jre-alpine` solo con el JRE, usuario no root

## Pipeline CI/CD

El pipeline `.github/workflows/deploy-backend.yml` se activa con push en la rama `deploy` y ejecuta:

1. **Build** — construye la imagen Docker multi-stage
2. **Push** — publica la imagen en Amazon ECR con tags `latest` y `sha`
3. **Deploy** — vía AWS SSM envía comando a la EC2 Backend para hacer pull y reiniciar el contenedor

### Secrets requeridos en GitHub

| Secret | Descripción |
|--------|-------------|
| AWS_ACCESS_KEY_ID | Credencial AWS Academy |
| AWS_SECRET_ACCESS_KEY | Credencial AWS Academy |
| AWS_SESSION_TOKEN | Token de sesión AWS Academy |
| AWS_REGION | Región (us-east-1) |
| ECR_REGISTRY | URL base del registry ECR |
| ECR_REPO_URL_BACKEND | URL completa del repo ECR backend |
| EC2_BACKEND_INSTANCE_ID | ID de la instancia EC2 backend (i-xxxx) |
| DB_ENDPOINT | IP privada del EC2 con la BD |
| DB_NAME | Nombre de la base de datos |
| DB_USERNAME | Usuario de la base de datos |
| DB_PASSWORD | Contraseña de la base de datos |
"# deploy trigger" 
