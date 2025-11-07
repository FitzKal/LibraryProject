# --------------------
# STAGE 1: BUILD STAGE (Compiles the Java code and creates the JAR)
# --------------------
FROM gradle:8-jdk23-alpine AS builder
WORKDIR /app

COPY build.gradle settings.gradle ./
COPY src ./src
RUN gradle build -x test --no-daemon

FROM eclipse-temurin:23-jre-alpine


# STAGE 2: Run stage
WORKDIR /app

# The JAR file is copied from the build stage (AS builder)
# We copy the specific JAR file created in the build stage
COPY --from=builder /app/build/libs/demo-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-jar","app.jar"]