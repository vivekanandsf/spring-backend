# Build stage
FROM gradle:7.4.0-jdk11-hotspot AS build
COPY . .
RUN ./gradlew clean build --no-daemon

# Package stage
FROM openjdk:11.0.11-jre-slim
COPY --from=build /home/gradle/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
