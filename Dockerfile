#build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY .env .
COPY src/ src/
RUN mvn clean package -DskipTests

#run
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/stencilweb-0.0.1-SNAPSHOT.jar app.jar

COPY wait-for-it.sh /wait-for-it.sh
RUN apt-get update && apt-get install -y dos2unix \
    && dos2unix /wait-for-it.sh
RUN chmod +x /wait-for-it.sh


EXPOSE 8080
# Wait for MySQL before launching the app
ENTRYPOINT ["/wait-for-it.sh", "mysqldb:3306", "--timeout=60", "--", "java", "-jar", "app.jar"]
#ENTRYPOINT ["java", "-jar", "app.jar"]
