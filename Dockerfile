# Build stage
FROM maven:3.8.6-openjdk-21 AS build

# Set the working directory
WORKDIR /app

# Copy all files from the current directory to the working directory in the image
COPY . .

# Build the project and skip tests
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:21-jdk-slim

# Set the working directory for the runtime container
WORKDIR /app

# Copy the WAR file from the build stage to the runtime stage
COPY --from=build /app/target/DrComputer-0.0.1-SNAPSHOT.war drcomputer.war

# Expose the application on port 8080
EXPOSE 8080c

# Set the command to run the application
ENTRYPOINT ["java", "-jar", "drcomputer.war"]
