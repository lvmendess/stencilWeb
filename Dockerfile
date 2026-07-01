# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY .env .
COPY src/ src/
RUN mvn clean package -DskipTests -Dmaven.javadoc.skip=true -P deployment

# Runtime stage
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Create user, assign directories, and set permissions in the correct stage
RUN useradd -m appuser && \
    mkdir -p /uploads/images && \
    chown -R appuser:appuser /uploads/images /app

COPY --from=build /app/target/stencilweb-0.0.1-SNAPSHOT.jar app.jar
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# Switch to the created non-root user
USER appuser

EXPOSE 8080

ENTRYPOINT ["/wait-for-it.sh", "mysqldb:3306", "--timeout=200", "--", "java", "-jar", "app.jar"]