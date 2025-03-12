package application.controller;

import application.actions.*;
import application.entity.Todo;
import application.repository.KeyValueTodoRepository;
import application.service.KeyValueTodoService;
import application.service.TodoService;
import lombok.Data;

import java.io.IOException;
import java.time.DateTimeException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

@Data
public class ConsoleTodoController {

    private final Scanner scanner;
    private final TodoService todoService;
    private Map<String, Action> actionMap;
    private Boolean isStopped = false;


    public void sayHello() {
        System.out.println("Здравствуйте! Это консольное приложения для учета ваших задач! \nДля получения списка команд вводи \"help\"");
    }

    public void launch() {
        sayHello();
        actionMap = initActionMap();
        while (!isStopped) {
            System.out.println("Ожидается ввод следующей команды: ");
            try {
                String input = scanner.nextLine();
                if (input.equals("exit")) {
                    isStopped = true;
                    continue;
                }
                Action action = inputProcessing(input);
                action.execute();
            } catch (IOException | DateTimeException e) {
                System.out.println("Некорректный ввод, попробуйте снова");
            } catch (Exception e) {
                System.out.println("Непредусмотренная ошибка\n" + e.getMessage());
            }

        }
        scanner.close();
        System.out.println("Хорошего дня, ваши задачки уже стёрты!");
    }

    public Action inputProcessing(String input) throws IOException {
        return Optional.ofNullable(actionMap.get(input))
                .orElseThrow(() -> new IOException(String.format("Для ввода: %s не предусмотрено логики", input)));
    }

    public Map<String, Action> initActionMap() {
        Map<String, Action> actionMap = new HashMap<>();
        actionMap.put("add", new AddTodoAction(todoService, scanner));
        actionMap.put("list", new FindTodosAction(todoService));
        actionMap.put("filter", new FindTodosByStatusAction(todoService, scanner));
        actionMap.put("delete", new RemoveTodoAction(todoService, scanner));
        actionMap.put("sort", new SortTodosAction(todoService, scanner));
        actionMap.put("edit", new UpdateAction(todoService, scanner));
        actionMap.put("help", new HelpAction());

        return actionMap;
    }

    public static ConsoleTodoController init() {
        return new ConsoleTodoController(
                new Scanner(System.in),
                new KeyValueTodoService(new KeyValueTodoRepository(new HashMap<Integer, Todo>()))
        );
    }
}
