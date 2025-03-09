package application.actions;

import application.service.TodoService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SortTodoByDeadlineAction implements Action {

    private final TodoService todoService;

    @Override
    public void execute() {
        System.out.println("Сортируем задачи по сроку дедлайна");
        System.out.println(todoService.sortByDeadline());
        System.out.println("Задачи по сроку дедлайна успешно отсортированы!\n");
    }
}
