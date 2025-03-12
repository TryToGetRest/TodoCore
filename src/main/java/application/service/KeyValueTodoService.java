package application.service;

import application.entity.Todo;
import application.enums.Status;
import application.exceptions.TodoNotFoundException;
import application.repository.TodoRepository;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class KeyValueTodoService implements TodoService {

    private final TodoRepository todoRepository;


    @Override
    public void updateStatus(Integer id, Status status) {
        todoRepository.updateStatus(id, status);
    }

    @Override
    public void updateDescription(Integer id, String description) {
        todoRepository.updateDescription(id, description);
    }

    @Override
    public void updateTitle(Integer id, String newTitle) {
        todoRepository.updateTitle(id, newTitle);
    }

    @Override
    public void updateDeadline(Integer id, LocalDateTime deadLine) {
        todoRepository.updateDeadline(id, deadLine);
    }

    @Override
    public void saveTodo(Todo todo) {
        todoRepository.saveTodo(todo);
    }

    @Override
    public void removeTodo(Integer id) {
        todoRepository.removeTodo(id);
    }

    @Override
    public Map<Integer, Todo> findAllTodos() {
        return todoRepository.findAllTodos();
    }

    @Override
    public Todo findTodoById(Integer id) {
        return todoRepository.findTodoById(id).orElseThrow(() -> new TodoNotFoundException(String.format("Задача с номером %n не существует", id)));
    }

    @Override
    public Map<Integer, Todo> findByStatus(Status status) {
        return todoRepository.findAllTodos().entrySet().stream()
                .filter(o -> o.getValue().getStatus().equals(status))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public List<Todo> sortByDeadline() {
        return todoRepository.findAllTodos().entrySet().stream()
                .map(o -> o.getValue())
                .sorted(Comparator.comparing(Todo::getDeadline))
                .toList();
    }

    @Override
    public List<Todo> sortByStatus() {
        return todoRepository.findAllTodos().entrySet().stream()
                .map(o -> o.getValue())
                .sorted(Comparator.comparing(Todo::getStatus))
                .toList();
    }
}
