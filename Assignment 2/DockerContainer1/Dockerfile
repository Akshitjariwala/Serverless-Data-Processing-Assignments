FROM maven:3.8-jdk-11 as builder


WORKDIR /app
COPY pom.xml .
COPY src ./src


RUN mvn package -DskipTests

FROM adoptopenjdk/openjdk11:alpine-slim


COPY --from=builder /app/target/DockerContainer1-0.0.1-SNAPSHOT.jar /DockerContainer1-0.0.1-SNAPSHOT.jar


CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/DockerContainer1-0.0.1-SNAPSHOT.jar"]