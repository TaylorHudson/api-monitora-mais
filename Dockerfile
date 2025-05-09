FROM ubuntu:latest AS build

RUN apt-get update && apt-get install openjdk-17-jdk maven -y

COPY . .

RUN mvn clean install

FROM openjdk:17-jdk-slim
EXPOSE 8080

COPY --from=build /target/back-0.0.1-SNAPSHOT.jar /app/back.jar

ENTRYPOINT ["java", "-jar", "/app/back.jar" ]