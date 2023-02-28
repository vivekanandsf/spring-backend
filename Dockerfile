
#
# Build stage
#
FROM gradle:7.4-jdk11 AS build
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY src ./src
RUN gradle clean build -x test --no-daemon

#
# Package stage
#
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/build/libs/demo2-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
