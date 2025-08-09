# ChaTop Backend

Backend Spring Boot pour l'application ChaTop.

## Stack technique

- Java 17
- Spring Boot 3.1.3
- Maven 3.9
- MySQL 8.0

## Prérequis

- [Docker & Docker Compose](https://docs.docker.com/get-started/get-docker/)

## Installation

### Cloner le dépôt

```bash
git clone https://github.com/bem92/ChaTop_rentals_backend.git
cd ChaTop_backend_test
```

### Lancer les services

Utilisez un `docker-compose.yml` similaire à l'exemple suivant :

```yaml
version: "3.8"

services:
  mysql:
    image: mysql:8.0
    container_name: chatop-mysql
    restart: unless-stopped
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: chatop_db
      MYSQL_USER: chatop
      MYSQL_PASSWORD: chatop123
    volumes:
      - ./mysql-data:/var/lib/mysql
      - ./ressources/sql/script.sql:/docker-entrypoint-initdb.d/init.sql
    ports:
      - "3306:3306"

  backend:
    image: maven:3.9-eclipse-temurin-17
    container_name: chatop-backend
    working_dir: /app
    volumes:
      - ./:/app
    command: >
      mvn spring-boot:run
    ports:
      - "8080:8080"
    depends_on:
      - mysql
    environment:
      - SPRING_DATASOURCE_URL=jdbc:mysql://chatop-mysql:3306/chatop_db
      - SPRING_DATASOURCE_USERNAME=chatop
      - SPRING_DATASOURCE_PASSWORD=chatop123
      - JWT_SECRET=SPtp6SmaQMsgOdRhfz2uWBLULCbrVwdDa9jILVU8
      - JWT_EXPIRATION=3600000
```

Lancez les conteneurs :

```bash
docker-compose up -d
```

Cette commande démarre :

- **MySQL** avec la base `chatop_db` initialisée par `script.sql`
- **Le backend Spring Boot** accessible sur `http://localhost:8080`

## Utilisation

La documentation Swagger est disponible à l'adresse :

`http://localhost:8080/swagger-ui/index.html`

### Exemple de requêtes

```bash
# Inscription
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","name":"John Doe","password":"Password123!"}'

# Connexion
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"login":"user@example.com","password":"Password123!"}'
```

Les commandes ci-dessus sont identiques sous Linux, macOS ou Windows (PowerShell ou Git Bash).

Vous pouvez ensuite utiliser le token JWT obtenu lors de la connexion pour appeler les autres endpoints protégés.

