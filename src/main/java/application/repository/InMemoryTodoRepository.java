package application.repository;

import application.entity.Todo;
import application.enums.Status;
import application.exceptions.TodoNotFoundException;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Data
public class InMemoryTodoRepository {

    private final Map<Integer, Todo> storage;
    private static Integer idCounter = 0;

    public void saveTodo(String title, String description, Status status, LocalDateTime deadline) {
        storage.put(++idCounter, new Todo(title, description, status, deadline));
    }

    public void removeTodo(Integer id) {
        Todo removed = storage.remove(id);
        if (removed == null) {
            throw new TodoNotFoundException(formTodoNotFoundExceptionMessage(id));
        }
    }

    public Map<Integer, Todo> findAllTodos() {
        return storage;
    }

    public Optional<Todo> findTodoById(Integer id) {
        return Optional.ofNullable(storage.get(id));
    }

    public void updateStatus(Integer id, Status status) {
        Optional<Todo> todoById = findTodoById(id);
        Todo todoPersisted = todoById.orElseThrow(() -> new TodoNotFoundException(formTodoNotFoundExceptionMessage(id)));
        todoPersisted.setStatus(status);
    }

    public void updateDescription(Integer id, String description) {
        Optional<Todo> todoById = findTodoById(id);
        Todo todoPersisted = todoById.orElseThrow(() -> new TodoNotFoundException(formTodoNotFoundExceptionMessage(id)));
        todoPersisted.setDescription(description);
    }

    public void updateDeadline(Integer id, LocalDateTime deadLine) {
        Optional<Todo> todoById = findTodoById(id);
        Todo todoPersisted = todoById.orElseThrow(() -> new TodoNotFoundException(formTodoNotFoundExceptionMessage(id)));
        todoPersisted.setDeadline(deadLine);
    }

    public void updateTitle(Integer id, String newTitle) {
        Optional<Todo> todoById = findTodoById(id);
        Todo todoPersisted = todoById.orElseThrow(() -> new TodoNotFoundException(formTodoNotFoundExceptionMessage(id)));
        todoPersisted.setTitle(newTitle);
    }

    public void updateTodo(Integer id, Todo todo) {
        Optional<Todo> todoById = findTodoById(id);
        Todo todoPersisted = todoById.orElseThrow(() -> new TodoNotFoundException(formTodoNotFoundExceptionMessage(id)));
        bulkUpdate(todoPersisted, todo);
    }

    private void bulkUpdate(Todo persisted, Todo forUpdate) {
        persisted.setDeadline(forUpdate.getDeadline());
        persisted.setTitle(forUpdate.getTitle());
        persisted.setDescription(forUpdate.getDescription());
        persisted.setStatus(forUpdate.getStatus());
    }

    private String formTodoNotFoundExceptionMessage(Integer id) {
        return String.format("Задача с номером %n не существует", id);
    }
}
