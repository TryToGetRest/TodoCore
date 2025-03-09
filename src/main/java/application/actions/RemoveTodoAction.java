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
        System.out.println("Удаляем задачу, для этого введите её название: ");
        String title = scanner.nextLine();
        todoService.removeTodo(todoService.findTodoByTitle(title));
    }
}
