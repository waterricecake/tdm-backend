FROM openjdk:17-jdk-slim

ARG JAR_FILE=./build/libs/tdm-backend-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} tdm-backend.jar
ENTRYPOINT ["java","-jar","/tdm-backend.jar"]
