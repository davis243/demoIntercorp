# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

MAINTAINER David Sarmiento <david.sarmi@gmail.com>

# Make port 8080 available to the world outside this container
EXPOSE 8080

VOLUME /tmp

# The application's jar file
ARG JAR_FILE=target/demoIntercorp-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} app.jar

ENTRYPOINT exec java $JAVA_OPTS -jar app.jar