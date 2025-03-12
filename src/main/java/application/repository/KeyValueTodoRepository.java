package application.repository;

import application.entity.Todo;
import application.enums.Status;
import application.exceptions.TodoNotFoundException;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Data
public class KeyValueTodoRepository implements TodoRepository {

    private final Map<Integer, Todo> inMemoryStorage;
    private static Integer counter;

    public void saveTodo(Todo todo) {
        inMemoryStorage.put(++counter, todo);
    }

    public void removeTodo(Integer id) {
        if (findTodoById(id).isPresent()) {
            inMemoryStorage.remove(id);
        } else {
            throw new TodoNotFoundException(String.format("Задача с номером %n не существует", id));
        }
    }

    public Map<Integer, Todo> findAllTodos() {
        return inMemoryStorage;
    }

    public Optional<Todo> findTodoById(Integer id) {
        return Optional.ofNullable(inMemoryStorage.get(id));
    }

    public void updateStatus(Integer id, Status status) {
        Optional<Todo> todoById = findTodoById(id);
        Todo todoPersisted = todoById.orElseThrow(() -> new TodoNotFoundException(String.format("Задача с номером %n не существует", id)));
        todoPersisted.setStatus(status);
    }

    public void updateDescription(Integer id, String description) {
        Optional<Todo> todoById = findTodoById(id);
        Todo todoPersisted = todoById.orElseThrow(() -> new TodoNotFoundException(String.format("Задача с номером %n не существует", id)));
        todoPersisted.setDescription(description);
    }

    public void updateDeadline(Integer id, LocalDateTime deadLine) {
        Optional<Todo> todoById = findTodoById(id);
        Todo todoPersisted = todoById.orElseThrow(() -> new TodoNotFoundException(String.format("Задача с номером %n не существует", id)));
        todoPersisted.setDeadline(deadLine);
    }

    public void updateTitle(Integer id, String newTitle) {
        Optional<Todo> todoById = findTodoById(id);
        Todo todoPersisted = todoById.orElseThrow(() -> new TodoNotFoundException(String.format("Задача с номером %n не существует", id)));
        todoPersisted.setTitle(newTitle);
    }

    public void updateTodo(Integer id, Todo todo) {
        Optional<Todo> todoById = findTodoById(id);
        Todo todoPersisted = todoById.orElseThrow(() -> new TodoNotFoundException(String.format("Задача с номером %n не существует", id)));
        bulkUpdate(todoPersisted, todo);
    }

    private void bulkUpdate(Todo persisted, Todo forUpdate) {
        persisted.setDeadline(forUpdate.getDeadline());
        persisted.setTitle(forUpdate.getTitle());
        persisted.setDescription(forUpdate.getDescription());
        persisted.setStatus(forUpdate.getStatus());
    }
}
