package application.actions;

import application.service.TodoService;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Scanner;

@RequiredArgsConstructor
public class SortTodosAction implements Action {

    private final TodoService todoService;
    private final Scanner scanner;

    @Override
    public void execute() throws IOException {
        System.out.println("Сортируем задачи, по статусу или по дедлайну?\n Корректный ввод: status, deadline\n");
        String sortBy = scanner.nextLine();

        if (sortBy.equals("status")) {
            System.out.println(todoService.sortByStatus());
            System.out.println("Задачи по статусу успешно отсортированы!\n");
        } else if (sortBy.equals("deadline")) {
            System.out.println(todoService.sortByDeadline());
            System.out.println("Задачи по статусу успешно отсортированы!\n");
        } else {
            throw new IOException();
        }


    }
}
