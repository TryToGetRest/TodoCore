package application.controller;

import application.entity.Todo;
import application.exceptions.ActionNotFoundException;
import application.repository.KeyValueTodoRepository;
import application.service.KeyValueTodoService;
import application.service.TodoService;
import lombok.Data;

import java.io.IOException;
import java.time.DateTimeException;
import java.util.HashMap;
import java.util.Scanner;

@Data
public class ConsoleTodoController {

    private final Scanner scanner;
    private final TodoService todoService;
    private Boolean isStopped = false;

    public void launch() {
        sayHello();
        while (!isStopped) {
            System.out.println("Ожидается ввод следующей команды: ");
            try {
                String input = scanner.nextLine();
                switch (input) {
                    case "exit" -> isStopped = true;
                    case "help" -> printHelp();
                    case "add" -> addTodo();
                    case "list" -> getAllTodos();
                    case "filter" -> filterTodos();
                    case "delete" -> deleteTodo();
                    case "sort" -> sortTodos();
                    case "edit" -> editTodo();
                    default ->
                            throw new ActionNotFoundException(String.format("Действие для ввода %s не найдено", input));
                }

            } catch (IOException | DateTimeException e) {
                System.out.println("Некорректный ввод, попробуйте снова");
            } catch (Exception e) {
                System.out.println("Необработанная ошибка\n" + e.getMessage());
            }

        }
        sayGoodbye();
    }

    public static ConsoleTodoController init() {
        return new ConsoleTodoController(
                new Scanner(System.in),
                new KeyValueTodoService(new KeyValueTodoRepository(new HashMap<Integer, Todo>()))
        );
    }

    public void sayHello() {
        System.out.println("Здравствуйте! Это консольное приложения для учета ваших задач! \nДля получения списка команд вводи \"help\"");
    }

    public void printHelp() {

    }

    public void sayGoodbye() {
        scanner.close();
        System.out.println("Хорошего дня, ваши задачки уже стёрты!");
    }

    public void addTodo() {

    }

    public void getAllTodos() {

    }

    public void filterTodos() {

    }

    public void deleteTodo() {

    }

    public void sortTodos() {

    }

    public void editTodo() {

    }
}
