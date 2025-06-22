FROM amazoncorretto:17.0.15-alpine3.21
LABEL authors="Elchin"
COPY target/Auth-Service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]