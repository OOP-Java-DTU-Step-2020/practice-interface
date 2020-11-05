# Практика на работу с интерфейсами

Создать приложение, которое использует паттерн [Команда](https://refactoring.guru/ru/design-patterns/command)

**Команда** — это поведенческий [паттерн проектирования](https://refactoring.guru/ru/design-patterns), 
который превращает запросы в объекты, позволяя передавать их как аргументы при вызове методов, 
ставить запросы в очередь, логировать их, а также поддерживать отмену операций.

Реализуйте интерфейс `Command`:

```java
public interface Command {
	void execute();
}
```
      
для поддержки следующих команд:

1. `help`

    Выводит на сообщение `Help executed`

2. `echo <text>`

    Выводит на консоль строку <text>

    Например:

    `echo Hello World`

    выведет:

    `Hello World`
    
3. `now`
    
    Выводит на экран текущую дату в миллисекундах начиная с 1 января 1970 года.
    Для получения времени используйте метод `System.currentTimeMillis()`

4.  `exit`

    Выводит на экран строку `Goodbye!` и завершает работу программы.
    Для завершения программы используйте метод `System.exit(0)`.

Каждая реализация - это отдельный класс реализующий интерфейс `Command`.

Рекомендуется создать отдельный пакет `org.itstep.command` в который поместить классы и интерфейс `Command`.

Команды вводятся с клавиатуры после запуска программы.

В случае если команда неизвестна, то необходимо вывести на экран сообщение `Error`.

Пример работы программы:

```
>>> help
Help executed
>>> now
1604586299340
>>> echo Hello World
Hello World
>>> exit
Goodbye!
```
