Требуется запустить docker-compose
Запускаем eureka-server
Запускаем микросервисы book-storage-service, book-tracker-service
Запускаем api-gateway
Переходим по url http://localhost:8081/main-service/swagger-ui/index.html (Если ошибка, то нужно перезапустить api-gateway)
Регистрируемся и получаем токен
В правом верхнем углу Swagger есть кнопка Authorize куда и вставляем токен
После этого все запросы будут работать корректно
