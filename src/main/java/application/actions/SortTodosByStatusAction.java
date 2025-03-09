package application.actions;

import application.service.TodoService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SortTodosByStatusAction implements Action {

    private final TodoService todoService;

    @Override
    public void execute() {
        System.out.println("Сортируем задачи по статусу");
        System.out.println(todoService.sortByStatus());
        System.out.println("Задачи по статусу успешно отсортированы!\n");
    }
}
