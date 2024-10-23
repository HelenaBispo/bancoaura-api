FROM ubuntu:latest AS build

RUN apt update
RUN apt install openjdk-21-jdk -y
COPY . .

RUN apt install maven -y
RUN mvn clean install

FROM amazoncorretto:23

EXPOSE 8080

COPY --from=build /target/internetbanking-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]