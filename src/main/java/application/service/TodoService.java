package application.service;

import application.entity.Todo;
import application.enums.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface TodoService {

    void updateStatus(Integer id, Status status);

    void updateDescription(Integer id, String description);

    void updateTitle(Integer id, String newTitle);

    void updateDeadline(Integer id, LocalDateTime deadLine);

    void saveTodo(Todo todo);

    void removeTodo(Integer id);

    Map<Integer, Todo> findAllTodos();

    Todo findTodoById(Integer id);

    Map<Integer, Todo> findByStatus(Status status);

    List<Todo> sortByDeadline();

    List<Todo> sortByStatus();
}
