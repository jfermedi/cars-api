FROM amazoncorretto:17

LABEL version="1.0"

EXPOSE 8080:8080

WORKDIR /app

COPY target/docker-demo-0.0.1-SNAPSHOT.jar /app/cars-api.jar

ENTRYPOINT ["java", "-jar", "cars-api.jar"]