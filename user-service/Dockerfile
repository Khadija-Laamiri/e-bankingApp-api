FROM amazoncorretto:21-al2023-headless

WORKDIR /app


RUN ./mvnw clean package -DskipTests

COPY target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
