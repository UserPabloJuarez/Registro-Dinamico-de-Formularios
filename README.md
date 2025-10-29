Formulario Service - Microservicio de Registro de Formularios

Descripción

Microservicio desarrollado con Spring Boot 3.4.1 y arquitectura reactiva para el registro dinámico de formularios. Incluye seguridad por API Key, documentación OpenAPI y contenerización con Docker.

Características Principales

- Arquitectura Reactiva (WebFlux + Reactive MongoDB)

- Seguridad por API Key configurable

- Validación de datos robusta

- Documentación OpenAPI 3.0 (Swagger)

- Contenerización con Docker Compose

- Pruebas unitarias y de integración

- Campos dinámicos adicionales

Requisitos Previos
Opción 1: Con Docker (Recomendado)
Docker 20.10+

Docker Compose 2.0+

Opción 2: Sin Docker
Java 21

Maven 3.9+

MongoDB 6.0+

Ejecución con Docker (Método Recomendado)
1. Clonar y preparar el proyecto
bash
git clone <tu-repositorio>
cd formulario-service
2. Construir y ejecutar con Docker Compose
bash
# Construir y levantar todos los servicios
docker-compose up --build

# Ejecutar en segundo plano
docker-compose up -d --build
3. Verificar que los servicios estén corriendo
bash
docker-compose ps
Servicios desplegados:

🔹 Aplicación: http://localhost:8080

🔹 MongoDB: localhost:27017

🔹 Swagger UI: http://localhost:8080/swagger-ui.html
