package utils;

import application.exceptions.TodoNotFoundException;
import application.service.TodoService;

public class TodoServiceUtils {

    public static int findIdByTitle(String title, TodoService service) {
        return service.findAllTodos().entrySet()
                .stream()
                .filter(entry -> entry.getValue().getTitle().equals(title))
                .findFirst().orElseThrow(() -> new TodoNotFoundException("todo not found exception"))
                .getValue().getId();
    }

    public static void dropTodos(TodoService service) {
        service.findAllTodos().keySet().forEach(service::removeTodo);
    }
}
