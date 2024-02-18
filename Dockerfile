FROM openjdk:17-jdk-slim

ARG JAR_FILE=tdm-backend-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} tdm-backend.jar
ENTRYPOINT ["java","-jar","/tdm-backend.jar"]
