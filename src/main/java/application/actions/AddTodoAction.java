package application.actions;

import application.entity.Todo;
import application.enums.Status;
import application.service.TodoService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Scanner;

@RequiredArgsConstructor
public class AddTodoAction implements Action {

    private final TodoService todoService;
    private final Scanner scanner;

    @Override
    public void execute() {
        System.out.println("Добавляем задачу в коллекцию!");
        System.out.println("Введите название задачи: ");
        String title = scanner.nextLine();
        System.out.println("Введите описание задачи: ");
        String description = scanner.nextLine();
        System.out.println("Введите статус задачи, например TODO, IN_PROGRESS, DONE: ");
        Status status = Status.valueOf(scanner.nextLine());
        System.out.println("Введите дедлайн задачи, например: 2025-04-01T00:00:00");
        LocalDateTime deadline = LocalDateTime.parse(scanner.nextLine());
        todoService.saveTodo(Todo
                .builder()
                .title(title)
                .description(description)
                .deadline(deadline)
                .status(status)
                .build());
        System.out.println("Задача успешно сохранена");
    }
}
