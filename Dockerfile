# =============================================
# STAGE 1 - BUILD
# Compila el proyecto con Maven
# =============================================
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder

WORKDIR /app

# Copiar pom.xml primero para cachear dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente y compilar
COPY src ./src
RUN mvn clean package -DskipTests -B

# =============================================
# STAGE 2 - RUNTIME
# Imagen final liviana solo con el JAR
# =============================================
FROM eclipse-temurin:17-jre-alpine

# Crear usuario no root por seguridad
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

# Copiar el JAR generado en el stage anterior
COPY --from=builder /app/target/*.jar app.jar

# Cambiar propietario al usuario no root
RUN chown appuser:appgroup app.jar

# Usar usuario no root
USER appuser

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
