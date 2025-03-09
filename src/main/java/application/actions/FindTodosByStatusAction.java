package application.actions;

import application.enums.Status;
import application.service.TodoService;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

@RequiredArgsConstructor
public class FindTodosByStatusAction implements Action {

    private final TodoService todoService;
    private final Scanner scanner;

    @Override
    public void execute() {
        System.out.println("Выводим все задачи по статусу, для этого введите статус который интересует: TODO, IN_PROGRESS, DONE");
        System.out.println(todoService.findByStatus(Status.valueOf(scanner.nextLine())));
    }
}
