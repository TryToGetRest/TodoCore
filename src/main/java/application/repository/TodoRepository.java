package application.repository;

import application.entity.Todo;
import application.enums.Status;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

public interface TodoRepository {

    void saveTodo(Todo todo);

    void removeTodo(Integer id);

    Map<Integer, Todo> findAllTodos();

    Optional<Todo> findTodoById(Integer id);

    void updateStatus(Integer id, Status status);

    void updateDescription(Integer id, String description);

    void updateDeadline(Integer id, LocalDateTime deadLine);

    void updateTitle(Integer id, String newTitle);
}
