package application.repository;

import application.entity.Todo;
import application.enums.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TodoRepository {

    void saveTodo(Todo todo);

    void removeTodo(Todo todo);

    List<Todo> findAllTodos();

    Optional<Todo> findTodoByTitle(String title);

    void updateStatus(String title, Status status);

    void updateDescription(String title, String description);

    void updateDeadline(String title, LocalDateTime deadLine);
}
