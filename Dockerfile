# Build stage
FROM maven:3.8.6-openjdk-21 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and download dependencies only if they change
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copy the rest of the application code
COPY src ./src

# Build the project, skipping tests
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:21-jdk-slim

# Set the working directory for the runtime container
WORKDIR /app

# Copy the WAR file from the build stage to the runtime stage
COPY --from=build /app/target/DrComputer-0.0.1-SNAPSHOT.war drcomputer.war

# Expose the application on port 8080
EXPOSE 8080

# Set the command to run the application
ENTRYPOINT ["java", "-jar", "drcomputer.war"]
