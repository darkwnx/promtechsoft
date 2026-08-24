# Этап 1: Сборка приложения
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Копируем только pom.xml для кэширования зависимостей
COPY pom.xml .

# Скачиваем все зависимости
RUN mvn dependency:go-offline -B

# Копируем исходный код
COPY src ./src
COPY frontend ./frontend

# Собираем JAR (пропускаем тесты для скорости)
RUN mvn clean package -DskipTests

# Этап 2: Запуск приложения
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Копируем собранный JAR
COPY --from=build /app/target/promtechsoft-1.0.0.jar app.jar

# Открываем порт
EXPOSE 8080

# Запускаем
ENTRYPOINT ["java", "-jar", "/app/app.jar"]