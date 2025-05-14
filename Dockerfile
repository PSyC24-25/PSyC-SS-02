# Usamos una imagen base de Maven con Java 11
FROM maven:3.8.1-openjdk-11

# Copiamos el código fuente al contenedor
COPY . /app

# Establecemos el directorio de trabajo
WORKDIR /app

# Compilamos el proyecto y mejoramos las clases para JPA si es necesario
RUN mvn compile

# Especificamos el comando que ejecutará tu aplicación
CMD ["mvn", "test"]
