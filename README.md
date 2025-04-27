# ChallengeTekton – Calculadora con porcentaje dinámico

## Descripción
API en Spring Boot que suma dos números y aplica un porcentaje dinámico (cacheado 30 min).  
Incluye registro de historial en PostgreSQL y manejo de errores.

---

## 🛠️ Prerrequisitos

- Java 21 (JDK)
- Maven 3.8+
- Docker & Docker Compose

---

## 🚀 Ejecución local

### 1. Base de datos

```bash
docker-compose up -d db
```

Esto levantará PostgreSQL en el puerto 5433 (mapeado al 5432 interno).

### 2. Arrancar la aplicación
```bash
mvn clean spring-boot:run
```
La API escuchará en http://localhost:8080.

### 🐳 Ejecución con Docker Compose
```bash
docker-compose up --build -d
```
Servicio db: PostgreSQL

Servicio api: tu aplicación Java 21

La API quedará disponible en http://localhost:8080.```

### 📚 Documentación de la API (Swagger UI)
Una vez arrancada la aplicación, abre:
```bash
http://localhost:8080/swagger-ui.html
```
Allí encontrarás la documentación interactiva de los endpoints.

### 📋 Endpoints
### 1. POST /api/calcular
   Descripción: Suma num1 + num2 y aplica el porcentaje dinámico.
### 2. GET /api/history
   Descripción: Devuelve el historial de todas las llamadas (fecha, endpoint, parámetros, resultado o error).

### 🧪 Ejecución de tests
```bash
mvn test
```