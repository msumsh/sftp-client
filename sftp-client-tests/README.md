# sftp-client-tests
Тесты для консольного приложения для работы с SFTP-сервера.

#### Структура
- /docs - содержит чек-лист и список тест-кейсов;
- /expect - содержит Expect-скрипты для теста отображения консольного меню, некорректного ввода в меню и завершения программы;
- /src/test - содержит исходный код автоматизированных тестов;
- /target - содержит исполняемый jar-файл.

#### Используемые технологии
- Java SE 8
- TestNG
- Maven
- IntelliJ IDEA

#### Сборка
```bash
mvn clean test-compile
```

#### Запуск
Перед запуском необходимо собрать клиента:
```bash
cd sftp-client
mvn clean install
```

Запуск тестов:
```bash
mvn test
```

#### Запуск Expect-скриптов
Перед запуском Expect-скриптов необходимо поднять тестовый SFTP-сервер в Docker:
```bash
cd ..
docker run -d \
  --name sftp-server \
  -p 22:22 \
  -v "$(pwd)/domains.json:/home/admin/domains.json" \
  atmoz/sftp \
  admin:12345:::upload
```
Данные для подключения соотвествуют данным из config.properties.example. Файл domains.json должен находиться в корне проекта.

Остановить контейнер
```bash
docker stop sftp-server
```
Запуск скрипта
```bash
cd ../sftp-client
expect ../sftp-client-tests/expect/connect_success.exp
```
Вместо connect_success.exp может быть использован любой другой скрипт.