# Используем официальный образ Eclipse Temurin
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/promtechsoft-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]