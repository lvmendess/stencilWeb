FROM maven:3.8.8-eclipse-temurin-21-alpine AS build
COPY src/ /src/
COPY pom.xml .
RUN mvn -f pom.xml clean package
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/target/springweb.jar"]