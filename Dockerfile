# Use a Java 11 runtime as the base image
FROM openjdk:11-jre-slim

# Copy the packaged Spring Boot application to the container
COPY target/notes-0.0.1.jar notes-0.0.1.jar

# Expose port 8080 to the host machine
EXPOSE 8080

# Set the default command to run the Spring Boot application
ENTRYPOINT ["java","-jar","/notes-0.0.1.jar"]
