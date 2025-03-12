package application.service;

import application.entity.Todo;
import application.enums.Status;
import application.enums.TodoFields;
import application.exceptions.ActionNotFoundException;
import application.exceptions.TodoNotFoundException;
import application.repository.InMemoryTodoRepository;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class TodoService {

    private final InMemoryTodoRepository inMemoryTodoRepository;

    public void updateStatus(Integer id, Status status) {
        inMemoryTodoRepository.updateStatus(id, status);
    }

    public void updateDescription(Integer id, String description) {
        inMemoryTodoRepository.updateDescription(id, description);
    }

    public void updateTitle(Integer id, String newTitle) {
        inMemoryTodoRepository.updateTitle(id, newTitle);
    }

    public void updateDeadline(Integer id, LocalDateTime deadLine) {
        inMemoryTodoRepository.updateDeadline(id, deadLine);
    }

    public void saveTodo(Todo todo) {
        inMemoryTodoRepository.saveTodo(todo);
    }

    public void removeTodo(Integer id) {
        inMemoryTodoRepository.removeTodo(id);
    }

    public Map<Integer, Todo> findAllTodos() {
        return inMemoryTodoRepository.findAllTodos();
    }

    public Todo findTodoById(Integer id) {
        return inMemoryTodoRepository.findTodoById(id)
                .orElseThrow(() -> new TodoNotFoundException(String.format("Задача с номером %n не существует", id)));
    }

    public Map<Integer, Todo> findByStatus(Status status) {
        return inMemoryTodoRepository.findAllTodos().entrySet().stream()
                .filter(o -> o.getValue().getStatus() == status)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Todo> sortBy(TodoFields field) {
        switch (field) {
            case STATUS -> {
                return inMemoryTodoRepository.findAllTodos().entrySet().stream()
                        .map(Map.Entry::getValue)
                        .sorted(Comparator.comparing(Todo::getStatus))
                        .toList();
            }
            case DEADLINE -> {
                return inMemoryTodoRepository.findAllTodos().entrySet().stream()
                        .map(Map.Entry::getValue)
                        .sorted(Comparator.comparing(Todo::getDeadline))
                        .toList();
            }
            default ->
                    throw new ActionNotFoundException(String.format("Действие для поля %s не найдено", field.name()));
        }
    }
}
