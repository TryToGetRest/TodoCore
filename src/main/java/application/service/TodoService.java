package application.service;

import application.entity.Todo;
import application.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

public interface TodoService {

    void updateStatus(String title, Status status);

    void updateDescription(String title, String description);

    void updateDeadline(String title, LocalDateTime deadLine);

    void saveTodo(Todo todo);

    void removeTodo(Todo todo);

    List<Todo> findAllTodos();

    Todo findTodoByTitle(String title);

    List<Todo> findByStatus(Status status);

    List<Todo> sortByDeadline();

    List<Todo> sortByStatus();
}
