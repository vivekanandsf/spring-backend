
# Build stage
FROM gradle:7.4-jdk11 AS build
COPY . .
RUN ./gradlew clean build

# Package stage
FROM openjdk:11-jdk-slim
COPY --from=build /build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

