# Usamos una imagen de Java 21
FROM eclipse-temurin:21-jdk-alpine

# Directorio de trabajo
WORKDIR /app

# Copiamos el archivo JAR (asegúrate de que el nombre coincida)
COPY target/*.jar app.jar

# Exponemos el puerto 8080
EXPOSE 8080

# Ejecutamos la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]