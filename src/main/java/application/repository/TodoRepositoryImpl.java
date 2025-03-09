package application.repository;

import application.entity.Todo;
import application.enums.Status;
import application.exceptions.TodoAlreadyExistException;
import application.exceptions.TodoNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Data
public class TodoRepositoryImpl implements TodoRepository {

    private final List<Todo> inMemoryStorage;

    @Override
    public void saveTodo(Todo todo) {
        Optional<Todo> todoByTitle = findTodoByTitle(todo.getTitle());
        if (todoByTitle.isEmpty()) {
            inMemoryStorage.add(todo);
        } else {
            throw new TodoAlreadyExistException(String.format("Задача с названием %s уже существует", todo.getTitle()));
        }
    }

    @Override
    public void removeTodo(Todo todo) {
        Optional<Todo> todoByTitle = findTodoByTitle(todo.getTitle());
        if (todoByTitle.isPresent()) {
            inMemoryStorage.remove(todo);
        } else {
            throw new TodoNotFoundException(String.format("Задачи с названием %s не существует", todo.getTitle()));
        }
    }

    @Override
    public List<Todo> findAllTodos() {
        return inMemoryStorage;
    }

    @Override
    public Optional<Todo> findTodoByTitle(String title) {
        return inMemoryStorage.stream()
                .filter(o -> o.getTitle().equalsIgnoreCase(title))
                .findFirst();
    }

    @Override
    public void updateStatus(String title, Status status) {
        Optional<Todo> todoByTitle = findTodoByTitle(title);
        if (todoByTitle.isPresent()) {
            todoByTitle.get().setStatus(status);
        } else {
            throw new TodoNotFoundException(String.format("Задачи с названием %s не существует", title));
        }
    }

    @Override
    public void updateDescription(String title, String description) {
        Optional<Todo> todoByTitle = findTodoByTitle(title);
        if (todoByTitle.isPresent()) {
            todoByTitle.get().setDescription(description);
        } else {
            throw new TodoNotFoundException(String.format("Задачи с названием %s не существует", title));
        }
    }

    @Override
    public void updateDeadline(String title, LocalDateTime deadLine) {
        Optional<Todo> todoByTitle = findTodoByTitle(title);
        if (todoByTitle.isPresent()) {
            todoByTitle.get().setDeadline(deadLine);
        } else {
            throw new TodoNotFoundException(String.format("Задачи с названием %s не существует", title));
        }
    }
}
