# Проект по автоматизации API для [DemoQa](https://demoqa.com/)
<p align="center">
<a href="https://demoqa.com/"><img src='media/icons/DemoQa.jpg' width="35%"/></a>
</p>

## Структура

* <a href="#tools">Стек</a>
* <a href="#cases">Реализованные проверки</a>
* <a href="#console">Запуск из терминала</a>
* <a href="#jenkins">Сборка в Jenkins</a>
* <a href="#allure_TestOps">Интеграция с Allure TestOps</a>
* <a href="#allure">Allure отчет</a>
* <a href="#telegram">Уведомление в Telegram при помощи бота</a>
---

<a id="tools"></a>
## Стек

<p align="center">  
<a href="https://www.java.com/"><img src='media/icons/java.svg' width="50"/></a> 
<a href="https://junit.org/junit5/"><img src='media/icons/junit.svg' width="50"/></a>
<a href="https://rest-assured.io/"><img src='media/icons/rest_assured.png' width="50"/></a>
<a href="https://qameta.io/"><img src='media/icons/Allure_TO.svg' width="50"/></a>
<a href="https://allurereport.org/"><img src='media/icons/Allure_Report.svg' width="50"/></a>
<a href="https://selenide.org/"><img src='media/icons/Selenide.svg' width="50"/></a> 
<a href="https://aerokube.com/selenoid/"><img src='media/icons/Selenoid.svg' width="50"/></a> 
<a href="https://gradle.org/"><img src='media/icons/gradle.svg' width="50"/></a> 
<a href="https://www.jenkins.io/"><img src='media/icons/jenkins.svg' width="50"/></a> 
<a href="https://web.telegram.org/"><img src='media/icons/telegram.svg' width="50"/></a> 
<a href="https://github.com/"><img src='media/icons/github.svg' width="50"/></a> 
<a href="https://www.jetbrains.com/idea/"><img src='media/icons/intellij.svg' width="50"/></a> 
</p>

* В данном проекте автотесты написаны на языке Java.
* В качестве сборщика использован - Gradle.
* Использованы:
  * фреймворки JUnit 5 и Selenide
  * библиотека REST Assured.
* Удаленный прогон тестов запускается в Selenoid.
* Для удаленного запуска реализована джоба в Jenkins с формированием Allure-отчета и отправкой результатов в Telegram при помощи бота.

---
<a id="cases"></a>
## Реализованные проверки
- API:
  - POST /Account/v1/Login - авторизация пользователя
  - POST /Account/v1/User?UserId={UserId} - удаление пользователя
  - DELETE /BookStore/v1/Books?UserId={UserId} - удаление всех книг
  - POST /BookStore/v1/Books - добавление книги
  - DELETE /BookStore/v1/Books?UserId={UserId} - удаление книги
- API + UI:
  - Удаление книги в профиле пользователя
---
<a id="console"></a>
## <img src='media/icons/gradle.svg' width="50"/> Запуск из терминала

Локальный запуск:
```
./gradlew clean test
```
Удаленный запуск:
```
clean test
-DbrowserSize=${BROWSER_SIZE}  
-DremoteUrl=${SELENOID_URL}
```

---
<a id="jenkins"></a>
## <img src='media/icons/jenkins.svg' width="50"/> Сборка в Jenkins

[Сборка в Jenkins](https://jenkins.autotests.cloud/job/DecompositionUIHomework)

<img src='media/icons/СкринСборкиJenkins3.jpg'/>

### Параметры сборки в Jenkins:

* `${BROWSER_SIZE}` - разрешение бразуера. По умолчанию - 1920x1080 - chrome.
* `${SELENOID_URL}` - адрес удаленного сервера Selenoid.

---
<a id="allure_TestOps"></a>
## <img src='media/icons/Allure_TO.svg' width="50"/> Интеграция с Allure TestOps
[Ссылка на Allure TestOps](https://allure.autotests.cloud/launch/50493)

* ### Главный экран запуска
<img src='media/icons/Результат запуска.jpg'/>

* ### Страница с проведенными тестами
<img src='media/icons/Страница теста.jpg'/>

---
<a id="allure"></a>
## <img src='media/icons/Allure_Report.svg' width="50"/>Allure отчет
[Allure отчет](https://jenkins.autotests.cloud/job/DecompositionUIHomework/38/allure)

* ### Главный экран отчета
<img src='media/icons/Главный экран отчёта2.jpg'/>

* ### Страница с проведенными тестами
<img src='media/icons/Страница теста2.jpg'/>

Содержание Allure-отчета:
* Шаги теста
* Скриншот страницы на последнем шаге
* Page Source
* Логи браузерной консоли.

---
<a id="telegram"></a>
## <img src='media/icons/telegram.svg' width="50"/> Уведомление в Telegram при помощи бота

<img src='media/icons/tg.jpg'/>

---
