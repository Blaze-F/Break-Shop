# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory to /app
WORKDIR /app

# Copy the project directory into the container at /app
COPY . /app

ADD . .

## Install MySQL client and Redis server
#RUN apk add --no-cache mysql-client redis

EXPOSE 8090


# Build the application with Gradle
#RUN ./gradlew build -x test

# Start the application
CMD ["java", "-jar", "build/libs/breakshop-0.0.1-SNAPSHOT.jar"]
