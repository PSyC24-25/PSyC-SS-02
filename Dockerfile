# Usar Maven para construir la app
FROM maven:3.8.1-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Usar una imagen liviana de Java para ejecutar
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copiar el .jar desde el build anterior
COPY --from=build /app/target/*.jar app.jar

# Puerto expuesto por Spring Boot
EXPOSE 8080

# Ejecutar la app
CMD ["java", "-jar", "app.jar"]
