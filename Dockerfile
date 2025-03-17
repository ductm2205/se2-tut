# step 1: build stage

FROM maven:3.9-amazoncorretto-17 AS builder

WORKDIR /app

COPY pom.xml .

COPY /src ./src

RUN mvn clean package -DskipTests

# step 2: run stage

FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "app.jar" ]
