# ControlCoffeeMachine
Управление кофеваркой через браузер
-----------------------------------

Ветка проекта: `master`

─── master
    │
    BPP (Логгирование действий кофемашины в базу данных через Bean Post Processor)
   

___

Программа по управлению кофеваркой через браузер.

В программе используется [Spring Statemachine](https://docs.spring.io/spring-statemachine/docs/3.0.1/reference/) для 
моделирования состояний кофемашины и переходов между ними 
([StateMachineConfig.java](src/main/java/net/coffeemachine/config/StateMachineConfig.java)).
При переходах в новое состояние выполняются действия 
([паттерн 'Команда'](src/main/java/net/coffeemachine/service/statemachine/commands)).  

Кофемашина делает [3 вида кофе](src/main/java/net/coffeemachine/model/coffee/CoffeeType.java), кол-во ингредиентов и 
время приготовления для каждого вида находится в [consumables.yaml](src/main/resources/consumables.yaml), эти параметры 
внедряются в соответствующие классы по средствам `@Value`, например в [Cappuccino.java](src/main/java/net/coffeemachine/model/coffee/Cappuccino.java).

Для эмуляции действий кофемашины используется [CoffeeMachineEquipment.java](src/main/java/net/coffeemachine/service/CoffeeMachineEquipment.java).
Действия кофемашины логгируются в базу данных по средствам аннотации [@DatabaseLogging](src/main/java/net/coffeemachine/util/aspect/DatabaseLogging.java),
аспекта [DatabaseLoggingAspect.java](src/main/java/net/coffeemachine/util/aspect/DatabaseLoggingAspect.java) и 
DBAppender`а ([logback-spring.xml](src/main/resources/logback-spring.xml)).  

 

Конфигурация:  
    - [application.yaml](src/main/resources/application.yaml)  
    - [net.controlcoffeemachine.config](src/main/java/net/coffeemachine/config)  

---

### Требования

- JDK 11
- gradle 7.0 и выше

---

### Запуск


Запуск базы данных PostgreSQL
```
docker-compose up
```
Запуск приложения
```
/.gradlew build
```

```
URL: [http://localhost:8080/coffeemachine/control](http://localhost:8080/coffeemachine/control)
```
---

### Документация API

[Swagger Api Documentation](http://localhost:8080/coffeemachine/swagger-ui.html)

| API                     | Method | Description            | URL                                   |
|-------------------------|--------|------------------------|---------------------------------------|
| CoffeeMachineController | PATCH  | Включить кофеварку     | {URL}/start                           |
|                         | PATCH  | Сделать кофе           | {URL}/make?coffeeType={coffeeType}    |
|                         | PATCH  | Остатки ингредиентов   | {URL}/remains                         |
|                         | PATCH  | Почистить кофеварку    | {URL}/clean                           |
|                         | PATCH  | Выключить кофеварку    | {URL}/stop                            |

---
