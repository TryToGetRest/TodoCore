package application.actions;

import application.service.TodoService;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

@RequiredArgsConstructor
public class UpdateTodoTitleAction implements Action {

    private final TodoService todoService;
    private final Scanner scanner;

    @Override
    public void execute() {
        System.out.println("Обновляем название задачи, для этого введите текущее имя задачи: ");
        String title = scanner.nextLine();
        System.out.println("Теперь введите новое имя задачи: ");
        String newTitle = scanner.nextLine();
        todoService.updateDescription(title, newTitle);
        System.out.println("Описание задачи успешно обновлено!\n");
    }
}
