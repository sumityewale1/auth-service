
FROM eclipse-temurin:17-jdk

WORKDIR /auth-service

COPY target/auth-service-0.0.1-SNAPSHOT.jar auth-service.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "auth-service.jar"]
