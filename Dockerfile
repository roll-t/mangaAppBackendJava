# Use Maven with OpenJDK 17 (Eclipse Temurin distribution) for the build stage
FROM maven:3.8.6-eclipse-temurin-17 AS build

# Set the working directory
WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use a lightweight JDK image (Eclipse Temurin) to run the application
FROM eclipse-temurin:17-jre-alpine

# Set the working directory for the runtime container
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application on port 8080
EXPOSE 8080

# Set the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
