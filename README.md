# 🌡 Sensor Weather Logger

Spring Boot-приложение для учёта погодных замеров с различных сенсоров.  
Поддерживает REST API и веб-интерфейс (Thymeleaf), позволяет добавлять сенсоры, просматривать замеры и получать статистику.

---

## 🔧 Используемые технологии

- Java 17+
- Spring Boot
- Spring Web (REST + MVC)
- Spring Data JPA + Hibernate
- Thymeleaf (HTML-шаблоны)
- DTO (Data Transfer Objects)
- Обработка ошибок через `@ControllerAdvice`
- PostgreSQL / H2

---

## 🚀 Основные возможности

- 📡 **Добавление сенсоров** через веб или API
- 📋 **Просмотр списка сенсоров**
- ❌ **Удаление сенсоров**
- 🌡 **Добавление погодных замеров** (температура, был ли дождь)
- 🗓 **Просмотр всех замеров по дням**
- 📊 **Статистика**: сколько замеров всего, сколько из них с дождём
- 🌐 Удобный веб-интерфейс на Thymeleaf

---

## 📁 Сущности

### 🧭 Sensor
- `name` — уникальное имя сенсора

### 🌡 Measurement
- `temperature` — температура (например, 23.4)
- `raining` — был ли дождь (boolean)
- `timestamp` — дата и время замера
- `sensor` — связь с сенсором (ManyToOne)

## 🛠 Примеры REST API

### ➕ Добавить сенсор
POST /api/sensors/registration
Content-Type: application/json

{
"name": "Sensor-1"
}


### ➕ Добавить замер
POST /measurements/add
Content-Type: application/json

{
"temperature": 21.5,
"raining": true,
"sensor": {
  "name": "Sensor-1"
  }
}


### 📊 Получить все замеры
GET /measurements

### ☔ Кол-во дождливых дней
GET /measurements/rainyDaysCount

## 🖥 Веб-интерфейс

- `/sensors` — список сенсоров, добавление, удаление
- `/measurements` — список замеров, добавление замеров, список дождливых дней

## 📦 Сборка и запуск

```bash
./mvnw clean install
./mvnw spring-boot:run
Или в IntelliJ: Run -> Run Application


👨‍💻 Автор
Абылай Аубакиров
GitHub
