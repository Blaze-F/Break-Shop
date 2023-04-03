# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory to /app
WORKDIR /app

# Copy the project directory into the container at /app
COPY . /app

## Install MySQL client and Redis server
#RUN apk add --no-cache mysql-client redis

# Expose ports for MySQL and Redis servers
EXPOSE 3306 6379 6389 6399 6409

## Set environment variables
#ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/breakshop?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&serverTimezone=Asia/Seoul
#ENV SPRING_DATASOURCE_USERNAME=host
#ENV SPRING_DATASOURCE_PASSWORD=test1234
#ENV SPRING_REDIS_HOST=redis
#ENV SPRING_REDIS_PASSWORD=1234
#ENV SPRING_REDIS_SESSION_PORT=6379
#ENV SPRING_REDIS_CACHE_PORT=6389
#ENV SPRING_REDIS_CART_PORT=6399
#ENV SPRING_REDIS_RIDER_PORT=6409
#ENV FCM_RIDER_EXPIRE_SECOND=86400
#ENV SPRING_SESSION_STORE_TYPE=redis

# Build the application with Gradle
RUN ./gradlew build -x test

# Start the application
CMD ["java", "-jar", "build/libs/breakshop.jar"]
