# Автотесты формы входа ibank (создание пользователя через API)

## Запуск

1. Запустить SUT в тестовом режиме:  
   `java -Dspring.profiles.active=test -jar artifacts/app-ibank.jar`
2. Запустить тесты:  
   - Linux/macOS: `./gradlew test`  
   - Windows: `gradlew.bat test`

## Сборка в CI

![Java CI with Gradle](https://github.com/lolgaaleksandrovna93-hash/112/actions/workflows/gradle.yml/badge.svg)
