FROM amazoncorretto:21-al2023-headless
WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8084
#RUN npm install

CMD ["java", "-jar", "app.jar"]