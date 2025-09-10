## Nomo - Приложение для учета долгов

> **Backend** - Java + Spring Boot + PostgreSQL/Docker

> **Frontend** - Java + XML + Android


## Быстро посмотреть код

- [**Бэкенд**](https://github.com/teqnot/nomo-app/tree/main/backend/nomo/src/main/java/com/example/nomo)

- [**Фронтенд - Код**](https://github.com/teqnot/nomo-app/tree/main/frontend/app/src/main/java/com/example/nomo)

- [**Фронтенд - XML**](https://github.com/teqnot/nomo-app/tree/main/frontend/app/src/main/res)


## Требования
Для запуска **бэкенда** необходимо:
- Установленный Docker
- Установленный Docker Compose (обычно идет вместе с Docker Desktop)

Для запуска **фронтенда** (Android-приложения):
- Установленный Android Studio
- JDK 17+ (обычно поставляется с Android Studio)
- Эмулятор или физическое устройство Android

> Бэкенд и фронтенд запускаются **НЕЗАВИСИМО**. **СНАЧАЛА** поднимите бэкенд, **ЗАТЕМ** запускайте приложение.

Для начала работы:
```bash
git clone https://github.com/teqnot/nomo-app.git
cd nomo-app
```


## Запуск бэкенда

1. Перейдите в папку бэкенда
```bash
cd backend/nomo
```

2. Соберите и запустите проект одной командой
```bash
docker compose up --build
```
> При первом запуске сборка может занять >3х минут (скачивания образов, подтягивание зависимостей, сборка JAR)

3. Готово!

Когда в логах появится:
```bash
Test users created.
...
Tomcat started on port(s): 8080
Started NomoApplication in ... seconds
```
— сервер готов к работе.

## Запуск фронтенда
1. Откройте Android Studio.
2. Выберите "Open" -> выберите папку `frontend/`.
3. Дождитесь, пока Android Studio загрузит зависимости (Gradle sync)
4. Подключите эмулятор или физическое устройство
> Эмулятор в данном контексте лучше в силу того, что уже установлен нужный для доступа к бэкенда IP-адрес. 

> В случае использования физического устройства необходимо убедиться в том, что сервер и устройство находятся в одной локальной сети. Возможно придется изменить IP-адрес сервера в конфигурационных файлах приложения.