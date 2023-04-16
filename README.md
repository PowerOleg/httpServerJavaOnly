# Собираем Spring с нуля
Для запуска spring(не Spring Boot), помимо обычного написания контроллеров, сервисов, репозиториев необходимо:
<li>Main запускает встраиваемый Tomcat</li>
<li>Регистрация DispatcherServlet в классе SpringApplicationInitializer, который implements WebApplicationInitializer</li>
<li>Класс WebConfig для создания MessageConverter(), чтобы spring понял как конвертировать данные в тело View</li>