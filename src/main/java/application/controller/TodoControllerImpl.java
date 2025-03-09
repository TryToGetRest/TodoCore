package application.controller;

import application.actions.*;
import application.repository.TodoRepositoryImpl;
import application.service.TodoService;
import application.service.TodoServiceImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@RequiredArgsConstructor
@Data
public class TodoControllerImpl implements TodoController {

    private final Scanner scanner;
    private final TodoService todoService;
    private Map<String, Action> actionMap;
    private Boolean isStopped = false;

    @Override
    public void sayHello() {
        System.out.println("Здравствуйте! Это консольное приложения для учета ваших задач! \nДля получения списка команд вводи \"help\"");
    }

    @Override
    public void execute() {
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

        System.out.println("Хорошего дня, ваши задачки уже стёрты!");

    }

    @Override
    public Action inputProcessing(String input) throws IOException {
        return actionMap.entrySet().stream()
                .filter(o -> o.getKey().equals(input))
                .findFirst()
                .orElseThrow(() -> new IOException(String.format("Для ввода: %s не предусмотрено логики", input)))
                .getValue();
    }

    public Map<String, Action> initActionMap() {
        Map<String, Action> actionMap = new HashMap<>();
        actionMap.put("add", new AddTodoAction(todoService, scanner));
        actionMap.put("list", new FindTodosAction(todoService));
        actionMap.put("filter", new FindTodosByStatusAction(todoService, scanner));
        actionMap.put("delete", new RemoveTodoAction(todoService, scanner));
        actionMap.put("sort by deadline", new SortTodoByDeadlineAction(todoService));
        actionMap.put("sort by status", new SortTodosByStatusAction(todoService));
        actionMap.put("edit deadline", new UpdateTodoDeadlineAction(todoService, scanner));
        actionMap.put("edit description", new UpdateTodoDescriptionAction(todoService, scanner));
        actionMap.put("edit status", new UpdateTodoStatusAction(todoService, scanner));
        actionMap.put("edit title", new UpdateTodoTitleAction(todoService, scanner));
        actionMap.put("help", new HelpAction());
        actionMap.put("null", new RepeatInputAction());

        return actionMap;
    }

    public static TodoController init() {
        return new TodoControllerImpl(
                new Scanner(System.in),
                new TodoServiceImpl(new TodoRepositoryImpl(new ArrayList<>()))
        );
    }
}
