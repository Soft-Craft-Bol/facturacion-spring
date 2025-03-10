# Imagen base de Java
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/facturador-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
