# Start from OpenJDK base image
FROM openjdk:17-jdk-slim

# Add a label for image metadata
LABEL maintainer="gunjiashok107@gmail.com"

# Set the working directory
WORKDIR /app

# Copy the built jar into the container
COPY target/accounts-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
