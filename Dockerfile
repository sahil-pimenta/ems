# Stage 1: Build the application
FROM maven:3.9.4-eclipse-temurin-17 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the project files (pom.xml and src folder) into the container
COPY pom.xml .
COPY src ./src

# Build the Spring Boot application using Maven
RUN mvn clean package -DskipTests

# Stage 2: Create a lightweight image to run the app
FROM eclipse-temurin:17-jdk-alpine

# Set working directory in the runtime container
WORKDIR /app

# Copy the JAR from the build stage to the runtime stage
COPY --from=builder /app/target/ems-*.jar ems.jar

# Expose the port your Spring Boot app runs on (default is 8080 but I am setting it to 8083)
EXPOSE 8083

# Command to run the application
ENTRYPOINT ["java", "-jar", "ems.jar"]