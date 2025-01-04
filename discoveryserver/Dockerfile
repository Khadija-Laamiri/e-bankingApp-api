#FROM openjdk:21-jdk-slim

#LABEL maintainer="discoveryserver"
#VOLUME /app
#ADD target/*.jar discoveryserver.jar

#ENTRYPOINT ["java","-jar","discoveryserver.jar"]
FROM amazoncorretto:21-al2023-headless
WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8761

CMD ["java", "-jar", "app.jar"]