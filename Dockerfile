# Use Maven with OpenJDK 17 (Eclipse Temurin distribution)
FROM maven:3.8.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

# Build the application
RUN mvn clean package

# Use a lightweight JDK image to run the application
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
