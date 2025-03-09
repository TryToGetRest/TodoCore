package application.actions;

import application.service.TodoService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Scanner;

@RequiredArgsConstructor
public class UpdateTodoDeadlineAction implements Action {

    private final TodoService todoService;
    private final Scanner scanner;

    @Override
    public void execute() {
        System.out.println("Обновляем дедлайн задачи, для этого введите имя задачи: ");
        String title = scanner.nextLine();
        System.out.println("Теперь введите новый дедлайн для задачи: ");
        LocalDateTime deadline = LocalDateTime.parse(scanner.nextLine());
        todoService.updateDeadline(title, deadline);
        System.out.println("Описание задачи успешно обновлено!\n");
    }
}
