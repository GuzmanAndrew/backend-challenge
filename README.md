# Backend Challenge API REST

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-green.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-enabled-blue.svg)](https://www.docker.com/)

API REST desarrollada con Spring Boot (Java 21) que implementa funcionalidades de cálculo con porcentaje dinámico, caché y registro asíncrono de historial de llamadas.

## Requisitos

- [Docker](https://www.docker.com/products/docker-desktop/) (versión 20.10 o superior)
- [Docker Compose](https://docs.docker.com/compose/install/) (versión 2.0 o superior)
- [Postman](https://www.postman.com/downloads/) (opcional, para pruebas)

## Instalación y Ejecución

### Usando Docker Compose

1. **Clonar el repositorio**:
   ```bash
   git clone <url-del-repositorio>
   cd backend-challenge
   ```

2. **Iniciar los servicios**:
   ```bash
   docker-compose up -d
   ```
   Este comando iniciará PostgreSQL y la aplicación Spring Boot en contenedores Docker.

3. **Verificar el estado de los servicios**:
   ```bash
   docker-compose ps
   ```

4. **Ver logs**:
   ```bash
   docker-compose logs -f api
   ```

5. **Detener los servicios**:
   ```bash
   docker-compose down
   ```

6. **Eliminar volúmenes y recursos** (útil para reiniciar desde cero):
   ```bash
   docker-compose down -v
   ```

La API estará disponible en: http://localhost:8080

## Endpoints

### 1. Cálculo con Porcentaje (POST /api/calculate)

Realiza la suma de dos números y aplica un porcentaje dinámico.

**Curl**:
```bash
curl --location 'http://localhost:8080/api/calculate' \
--header 'Content-Type: application/json' \
--data '{
    "num1": 10.2,
    "num2": 10.4
}'
```

### 2. Simular Fallo del Servicio Externo (POST /api/test/external-service/fail)

Configura el servicio externo para simular un fallo y probar el mecanismo de caché.

**Curl**:
```bash
curl --location --request POST 'http://localhost:8080/api/test/external-service/fail?shouldFail=true'
```

### 3. Consultar Historial de Llamadas (GET /api/history)

Devuelve el historial de llamadas a la API con paginación.

**Curl**:
```bash
curl --location 'http://localhost:8080/api/history?page=0&size=10'
```

### 4. Consultar Valor en Caché (GET /api/calculate/cache/percentage)

Verifica si hay un valor de porcentaje almacenado en la caché.

**Curl**:
```bash
curl --location 'http://localhost:8080/api/calculate/cache/percentage'
```

## Pruebas

### Postman Collection

El proyecto incluye una colección de Postman para probar todos los endpoints. Puedes importar el archivo `Backend Challenge Tenpo.postman_collection.json` en Postman.

### Escenario de Prueba: Fallo del Servicio Externo

Para probar el funcionamiento de la caché cuando el servicio externo falla:

1. Realiza una primera llamada a `/api/calculate` para almacenar un valor en caché
2. Configura el servicio para fallar con `/api/test/external-service/fail?shouldFail=true`
3. Realiza otra llamada a `/api/calculate` y observa que usa el valor en caché
4. Si no existe ningún valor en caché, genera una excepción

Con los logs podemos ver si usa el porcentaje de cache o el servicios externo