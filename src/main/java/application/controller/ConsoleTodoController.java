package application.controller;

import application.enums.ConsoleAction;
import application.enums.Status;
import application.enums.TodoFields;
import application.exceptions.ActionNotFoundException;
import application.exceptions.TodoAlreadyExistException;
import application.exceptions.TodoNotFoundException;
import application.service.TodoService;
import lombok.Data;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Scanner;

@Data
public class ConsoleTodoController {

    private final Scanner scanner;
    private final TodoService inMemoryTodoService;

    public void launch() {
        sayHello();
        while (true) {
            System.out.println("Ожидается ввод следующей команды: ");
            try {
                String input = scanner.nextLine().toUpperCase();
                switch (ConsoleAction.valueOf(input)) {
                    case EXIT -> {
                        sayGoodbye();
                        return;
                    }
                    case HELP -> printHelp();
                    case ADD -> addTodo();
                    case LIST -> getAllTodos();
                    case FILTER -> filterTodos();
                    case DELETE -> deleteTodo();
                    case SORT -> sortTodos();
                    case EDIT -> editTodo();
                    default ->
                            throw new ActionNotFoundException(String.format("Действие для ввода %s не найдено", input));
                }

            } catch (ActionNotFoundException | TodoAlreadyExistException | TodoNotFoundException |
                     DateTimeException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Необработанная ошибка\n" + e.getMessage());
            }

        }
    }

    private void sayHello() {
        System.out.println("Здравствуйте! Это консольное приложения для учета ваших задач! \nДля получения списка команд вводи \"help\"");
    }

    private void printHelp() {
        System.out.println(
                """
                        Для того, что бы добавить задачу, вводи: "add"
                        Для того, что бы вывести все задачи, вводи: "list"
                        Для того, что бы получить задачи по статусу, вводи: "filter"
                        Для того, что бы удалить задачу, вводи: "delete"
                        Для того, что бы сортировать задачи, вводи: "sort"
                        Для того, что бы изменить задачу, вводи: "edit"
                        Для того, что бы выйти из приложения, вводи: "exit"
                        """
        );
    }

    private void sayGoodbye() {
        scanner.close();
        System.out.println("Хорошего дня, ваши задачки уже стёрты!");
    }

    private void addTodo() {
        System.out.println("Добавляем задачу в коллекцию!");
        System.out.println("Введите название задачи: ");
        String title = scanner.nextLine();
        System.out.println("Введите описание задачи: ");
        String description = scanner.nextLine();
        System.out.println("Введите статус задачи, например TODO, IN_PROGRESS, DONE: ");
        Status status = Status.valueOf(scanner.nextLine().toUpperCase());
        System.out.println("Введите дедлайн задачи, например: 2025-04-01T00:00:00");
        LocalDateTime deadline = LocalDateTime.parse(scanner.nextLine());
        inMemoryTodoService.saveTodo(title, description, status, deadline);
        System.out.println("Задача успешно сохранена");
    }

    private void getAllTodos() {
        System.out.println("Вывод всех задач:");
        inMemoryTodoService.findAllTodos().entrySet().forEach(System.out::println);
    }

    private void filterTodos() {
        System.out.println("Выводим все задачи по статусу, для этого введите статус который интересует: TODO, IN_PROGRESS, DONE");
        System.out.println(inMemoryTodoService.findByStatus(Status.valueOf(scanner.nextLine())));
    }

    private void deleteTodo() {
        System.out.println("Удаляем задачу, для этого введите её номер: ");
        int id = scanner.nextInt();
        inMemoryTodoService.removeTodo(id);
    }

    private void sortTodos() {
        System.out.println("Сортируем задачи, по статусу или по дедлайну?\n" + "Корректный ввод: status, deadline\n");
        String input = scanner.nextLine().toUpperCase();
        TodoFields field = TodoFields.valueOf(input);
        System.out.println(inMemoryTodoService.sortBy(field));
        System.out.println("Задачи успешно отсортированы!\n");
    }

    private void editTodo() {
        System.out.println("Обновляем задачу, для этого введите номер задачи: ");
        int id = scanner.nextInt();
        System.out.println("Теперь введите, что хотите обновить: \nКорректный ввод: title, description, status, deadline\n");
        String input = scanner.nextLine().toUpperCase();
        switch (TodoFields.valueOf(input)) {
            case TITLE -> {
                System.out.println("Теперь введите новое имя задачи: ");
                String newTitle = scanner.nextLine();
                inMemoryTodoService.updateTitle(id, newTitle);
                System.out.println("Название задачи успешно обновлено!\n");
            }
            case DESCRIPTION -> {
                System.out.println("Теперь введите новое описание задачи: ");
                String newDescription = scanner.nextLine();
                inMemoryTodoService.updateDescription(id, newDescription);
                System.out.println("Описание задачи успешно обновлено!\n");
            }
            case STATUS -> {
                System.out.println("Теперь введите новый статус:\nКорректный ввод: TODO, IN_PROGRESS, DONE\n");
                Status newStatus = Status.valueOf(scanner.nextLine());
                inMemoryTodoService.updateStatus(id, newStatus);
                System.out.println("Статус задачи успешно обновлен!\n");
            }
            case DEADLINE -> {
                System.out.println("Теперь введите новый дедлайн для задачи:\nКорректный ввод: 2025-04-30T00:00:00\n");
                LocalDateTime deadline = LocalDateTime.parse(scanner.nextLine());
                inMemoryTodoService.updateDeadline(id, deadline);
                System.out.println("Дедлайн задачи успешно обновлен!\n");
            }
            default -> throw new ActionNotFoundException(String.format("Действие для ввода %s не найдено", input));
        }
    }
}
