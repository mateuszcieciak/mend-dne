# Stage 1: Build the application with Maven
FROM maven:3.8.7-eclipse-temurin-19 AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ /app/src/
RUN mvn package

# Stage 2: Run the application with OpenJDK
FROM azul/zulu-openjdk:19 AS runner

WORKDIR /app

COPY --from=builder /app/target/mend-dne-app-*.jar ./mend-dne-app.jar

EXPOSE 10000

ENTRYPOINT ["java", "-jar", "./mend-dne-app.jar"]