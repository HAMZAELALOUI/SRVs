# Use the official Maven image with JDK 17 to build the Spring Boot application
FROM maven:3.8.4-openjdk-17 AS build

# Copy the source code and Maven POM file into the image
COPY src /home/app/src
COPY pom.xml /home/app

# Build the application
WORKDIR /home/app
RUN mvn clean package -DskipTests

# Use OpenJDK 17 slim image for running the application
FROM openjdk:17-slim

# Copy the jar file from the build stage
COPY --from=build /home/app/target/*.jar /usr/local/lib/app.jar

# Expose the port the application uses
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "/usr/local/lib/app.jar"]
