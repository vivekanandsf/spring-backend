
#
# Build stage
#
FROM gradle:7.4-jdk11 AS build
COPY build.gradle settings.gradle ./
COPY src ./src
RUN gradle clean build -x test --no-daemon

#
# Package stage
#
FROM openjdk:11-jdk-slim
COPY --from=build /build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
