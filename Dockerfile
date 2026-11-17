FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/banking-service-0.0.1-SNAPSHOT.jar /app/banking-service.jar
EXPOSE 8096
ENTRYPOINT ["java", "-jar", "/app/banking-service.jar"]