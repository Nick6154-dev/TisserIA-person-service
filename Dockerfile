FROM eclipse-temurin:21-jre

COPY *.jar /app/person-service.jar

WORKDIR /app

CMD ["java", "-jar", "person-service.jar"]