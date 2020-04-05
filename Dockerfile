FROM openjdk:8-alpine

ADD ./target/friendmanagement-0.0.1-SNAPSHOT.jar /app/friendmanagement.jar
EXPOSE 8080/tcp

ENTRYPOINT ["java", "-jar", "/app/friendmanagement.jar", "--spring.profiles.active=prod"]
