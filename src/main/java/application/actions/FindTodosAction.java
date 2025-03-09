package application.actions;

import application.service.TodoService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindTodosAction implements Action {

    private final TodoService todoService;

    @Override
    public void execute() {
        System.out.println("Вывод всех задач:");
        todoService.findAllTodos().forEach(System.out::println);
    }

}
