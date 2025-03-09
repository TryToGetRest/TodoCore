package application.actions;

import application.enums.Status;
import application.service.TodoService;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

@RequiredArgsConstructor
public class UpdateTodoStatusAction implements Action {

    private final TodoService todoService;
    private final Scanner scanner;

    @Override
    public void execute() {
        System.out.println("Обновляем статус задачи, для этого введите имя задачи: ");
        String title = scanner.nextLine();
        System.out.println("Теперь введите новый статус: (TODO, IN_PROGRESS, DONE) ");
        Status status = Status.valueOf(scanner.nextLine());
        todoService.updateStatus(title, status);
        System.out.println("Статус задачи успешно обновлён!\n");
    }
}
