FROM openjdk:11-jre-slim
WORKDIR /app
COPY ./target/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
EXPOSE 9999

# FROM openjdk:8-jdk-alpine
# RUN apk update && apk add maven
# COPY . /project
# WORKDIR /project
# RUN mvn package
# ENTRYPOINT ["java", "-jar", "nethealth-user-api-0.0.1-SNAPSHOT.jar"]