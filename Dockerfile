# Using the latest version of Maven with OpenJDK 21
FROM maven:3.9.0-openjdk-21 AS build

# OR using a specific tag with a supported version
FROM maven:3.8.6-jdk-slim AS build

WORKDIR /app

COPY . .
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:21-jdk-slim
WORKDIR /app

COPY --from=build /app/target/DrComputer-0.0.1-SNAPSHOT.war drcomputer.war
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "drcomputer.war"]
