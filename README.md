# 🚀 PromTechSoft — Full-Stack Portfolio Project

Промышленная компания с полным стеком технологий. Демонстрационный проект для портфолио Java Developer.

## 🛠 Технологический стек

### Backend
- **Java 21**
- **Spring Boot 3.2.3**
- **Spring Security** + **JWT** аутентификация
- **Spring Data JPA** + **Hibernate**
- **PostgreSQL 18**
- **Lombok**
- **Swagger/OpenAPI** документация
- **JUnit 5** + **Mockito** + **MockMvc** для тестирования

### Frontend
- **HTML5**
- **CSS3**
- **JavaScript (ES6+)**
- Адаптивная верстка
- Анимации

### DevOps
- **Maven** сборка
- **GitHub Actions** CI/CD
- **Docker** (готов к использованию)

## 📋 Функциональность

### Blog API
- ✅ CRUD операции для постов
- ✅ Поиск по постам
- ✅ Фильтрация по категориям
- ✅ Валидация данных
- ✅ Пагинация (в разработке)

### Аутентификация
- ✅ Регистрация пользователей
- ✅ Вход с JWT токеном
- ✅ Роли пользователей (USER, ADMIN)
- ✅ Защита эндпоинтов
- ✅ BCrypt шифрование паролей

### Тестирование
- ✅ Unit тесты (Mockito)
- ✅ Интеграционные тесты (MockMvc)
- ✅ Тестовая H2 база данных

## 🏗 Архитектура
┌─────────────┐ ┌──────────────┐ ┌─────────────┐
│ Frontend │────▶│ REST API │────▶│ PostgreSQL │
│ (HTML/CSS) │ │ (Spring Boot)│ │ │
└─────────────┘ └──────────────┘ └─────────────┘
│
├────▶ JWT Security
│
├────▶ Swagger UI
│
└────▶ H2 (tests)

## 📂 Структура проекта
promtechsoft/
├── .github/
│ └── workflows/
│ └── ci.yml # CI/CD pipeline
├── frontend/
│ └── static/
│ ├── index.html
│ ├── services.html
│ ├── projects.html
│ ├── blog.html
│ ├── about.html
│ ├── contacts.html
│ ├── style.css
│ └── animations.css
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── com/promtechsoft/
│ │ │ ├── PromTechSoftApplication.java
│ │ │ ├── config/
│ │ │ │ └── SecurityConfig.java
│ │ │ ├── controller/
│ │ │ │ ├── AuthController.java
│ │ │ │ ├── BlogPostController.java
│ │ │ │ └── HomeController.java
│ │ │ ├── dto/
│ │ │ │ ├── AuthResponse.java
│ │ │ │ ├── BlogPostRequest.java
│ │ │ │ ├── BlogPostResponse.java
│ │ │ │ ├── LoginRequest.java
│ │ │ │ └── RegisterRequest.java
│ │ │ ├── entity/
│ │ │ │ ├── BlogPostEntity.java
│ │ │ │ └── UserEntity.java
│ │ │ ├── exception/
│ │ │ │ ├── GlobalExceptionHandler.java
│ │ │ │ └── ResourceNotFoundException.java
│ │ │ ├── repository/
│ │ │ │ ├── BlogPostRepository.java
│ │ │ │ └── UserRepository.java
│ │ │ ├── security/
│ │ │ │ └── JwtAuthenticationFilter.java
│ │ │ └── service/
│ │ │ ├── AuthService.java
│ │ │ ├── BlogPostService.java
│ │ │ ├── CustomUserDetailsService.java
│ │ │ └── JwtService.java
│ │ └── resources/
│ │ └── application.yml
│ └── test/
│ ├── java/
│ │ └── com/promtechsoft/
│ │ ├── controller/
│ │ │ └── BlogPostControllerIntegrationTest.java
│ │ └── service/
│ │ └── BlogPostServiceTest.java
│ └── resources/
│ └── application-test.yml
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md

## 🚀 Запуск проекта

### Требования

- Java 17+ (рекомендуется 21)
- Maven 3.8+
- PostgreSQL 16+

### Настройка базы данных

1. Создайте базу данных:
```sql
CREATE DATABASE promtechsoft;
```

2. Настройте application.yml:
```yaml
   spring:
   datasource:
   url: jdbc:postgresql://localhost:5432/promtechsoft
   username: postgres
   password: ваш_пароль
```
### Запуск
```bash
# Сборка
mvn clean package

# Запуск
java -jar target/promtechsoft-1.0.0.jar
```
### Или через IntelliJ IDEA:
1. Откройте проект
2. Запустите PromTechSoftApplication.java

### Доступ
- **Frontend:** http://localhost:8080/
- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **API:** http://localhost:8080/api/v1/

## 📚 API Endpoints
### Аутентификация
Method	Endpoint	Description	Auth
POST	/api/v1/auth/register	Регистрация	❌
POST	/api/v1/auth/login	Вход	❌
### Блог
Method	Endpoint	Description	Auth
GET	/api/v1/posts	Все посты	❌
GET	/api/v1/posts/{id}	Пост по ID	❌
POST	/api/v1/posts	Создать пост	✅
PUT	/api/v1/posts/{id}	Обновить пост	✅
DELETE	/api/v1/posts/{id}	Удалить пост	✅
GET	/api/v1/posts/search?query=	Поиск	❌
GET	/api/v1/posts/category/{category}	По категории	❌
## 🔐 Безопасность
- **JWT** токены для аутентификации
- **BCrypt** для хеширования паролей
- **Роли:** USER, ADMIN
- **Stateless** сессии
- **CSRF** отключен (API)
## 🧪 Тестирование
```bash
  # Все тесты
  mvn test
  # Только unit тесты
  mvn test -Dtest=BlogPostServiceTest
  # Только интеграционные
  mvn test -Dtest=BlogPostControllerIntegrationTest
```
## 🐳 Docker
```bash
# Сборка образа
docker build -t promtechsoft:1.0.0 .
# Запуск с PostgreSQL
docker-compose up -d
# Остановка
docker-compose down
```
## 📊 CI/CD
**GitHub Actions** автоматически:
- Запускает тесты при push
- Собирает JAR
- Загружает артефакты
## 🎯 Что демонстрирует проект
- ✅ Spring Boot REST API
- ✅ Spring Security + JWT
- ✅ JPA/Hibernate
- ✅ PostgreSQL
- ✅ Валидация данных
- ✅ Обработка ошибок
- ✅ Swagger документация
- ✅ Unit и Integration тесты
- ✅ Maven
- ✅ CI/CD
- ✅ Чистая архитектура (Controller → Service → Repository)
- ✅ DTO паттерн
- ✅ Lombok
- ✅ Логирование
## 📝 Лицензия
**MIT License** — используйте свободно для портфолио.
## 👤 Автор
**Радомир Рахманов**
- **GitHub:** @darkwnx
## ⭐ Не забудьте поставить звезду, если проект полезен!