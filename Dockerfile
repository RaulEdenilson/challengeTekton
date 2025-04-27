# 1) Build stage con Maven + OpenJDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# 2) Runtime stage con Temurin 21
FROM eclipse-temurin:21-jre
WORKDIR /app

# Ajusta el patrón si tu JAR se llama distinto
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]