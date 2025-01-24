
# Step 1: Use an official Gradle image to build the application
FROM gradle:8.3.0-jdk17 AS build


ENV OTEL_EXPORTER_OTLP_ENDPOINT=http://host.docker.internal:4318
ENV OTEL_EXPORTER_OTLP_PROTOCOL=http
ENV OTEL_SERVICE_NAME=airlines
ENV OTEL_RESOURCE_ATTRIBUTES=deployment.environment=production


# Set the working directory inside the container
WORKDIR /app

# Copy the entire project to the container
COPY . .

# Build the application
RUN gradle clean bootJar

# Step 2: Use a lightweight JDK image for running the app
FROM openjdk:17-jdk-slim

# Set the working directory for the runtime container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v2.12.0/opentelemetry-javaagent.jar /app/grafana-opentelemetry-java.jar



# Expose the port the application runs on
EXPOSE 8080

# Set the command to run the application
ENTRYPOINT ["java", "-javaagent:/app/grafana-opentelemetry-java.jar", "-jar", "app.jar"]


