FROM openjdk:22-jdk-slim AS builder

RUN apt-get update && apt-get install -y maven

WORKDIR /app
COPY . .

RUN chmod +x ./mvnw

RUN ./mvnw clean package -DskipTests

FROM openjdk:22-jdk-slim

RUN addgroup --system spring && adduser --system --ingroup spring spring

COPY --from=builder /app/target/*.jar /app/app.jar

RUN chown -R spring:spring /app
USER spring

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]