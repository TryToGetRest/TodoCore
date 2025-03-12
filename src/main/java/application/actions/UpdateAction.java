package application.actions;

import application.enums.Status;
import application.service.TodoService;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

@RequiredArgsConstructor
public class UpdateAction implements Action {

    private final TodoService todoService;
    private final Scanner scanner;

    @Override
    public void execute() throws IOException {
        System.out.println("Обновляем задачу, для этого введите номер задачи: ");
        int id = scanner.nextInt();
        System.out.println("Теперь введите, что хотите обновить: \nКорректный ввод: title, description, status, deadline\n");
        String input = scanner.nextLine();

        switch (input) {
            case "title" -> {
                System.out.println("Теперь введите новое имя задачи: ");
                String newTitle = scanner.nextLine();
                todoService.updateTitle(id, newTitle);
                System.out.println("Название задачи успешно обновлено!\n");
            }
            case "description" -> {
                System.out.println("Теперь введите новое описание задачи: ");
                String newDescription = scanner.nextLine();
                todoService.updateDescription(id, newDescription);
                System.out.println("Описание задачи успешно обновлено!\n");
            }
            case "status" -> {
                System.out.println("Теперь введите новый статус:\nКорректный ввод: TODO, IN_PROGRESS, DONE\n");
                Status newStatus = Status.valueOf(scanner.nextLine());
                todoService.updateStatus(id, newStatus);
                System.out.println("Статус задачи успешно обновлен!\n");
            }
            case "deadline" -> {
                System.out.println("Теперь введите новый дедлайн для задачи:\nКорректный ввод: 2025-04-30T00:00:00\n");
                LocalDateTime deadline = LocalDateTime.parse(scanner.nextLine());
                todoService.updateDeadline(id, deadline);
                System.out.println("Дедлайн задачи успешно обновлен!\n");
            }
            default -> throw new IOException();
        }
    }
}
