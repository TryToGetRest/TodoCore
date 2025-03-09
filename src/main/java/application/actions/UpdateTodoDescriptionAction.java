package application.actions;

import application.service.TodoService;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

@RequiredArgsConstructor
public class UpdateTodoDescriptionAction implements Action {

    private final TodoService todoService;
    private final Scanner scanner;

    @Override
    public void execute() {
        System.out.println("Обновляем описание задачи, для этого введите имя задачи: ");
        String title = scanner.nextLine();
        System.out.println("Теперь введите новое описание задачи: ");
        String description = scanner.nextLine();
        todoService.updateDescription(title, description);
        System.out.println("Описание задачи успешно обновлено!\n");
    }
}
