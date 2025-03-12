package application.actions;

import application.service.TodoService;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

@RequiredArgsConstructor
public class RemoveTodoAction implements Action {

    private final TodoService todoService;
    private final Scanner scanner;

    @Override
    public void execute() {
        System.out.println("Удаляем задачу, для этого введите её номер: ");
        int id = scanner.nextInt();
        todoService.removeTodo(id);
    }
}
