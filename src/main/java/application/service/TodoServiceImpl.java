package application.service;

import application.entity.Todo;
import application.enums.Status;
import application.exceptions.TodoNotFoundException;
import application.repository.TodoRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Data
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    public void updateStatus(String title, Status status) {
        try {
            todoRepository.updateStatus(title, status);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateDescription(String title, String description) {
        try {
            todoRepository.updateDescription(title, description);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateDeadline(String title, LocalDateTime deadLine) {
        try {
            todoRepository.updateDeadline(title, deadLine);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveTodo(Todo todo) {
        try {
            todoRepository.saveTodo(todo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeTodo(Todo todo) {
        try {
            todoRepository.removeTodo(todo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Todo> findAllTodos() {
        return todoRepository.findAllTodos();
    }

    @Override
    public Todo findTodoByTitle(String title) {
        return todoRepository.findTodoByTitle(title)
                .orElseThrow(() -> new TodoNotFoundException(String.format("Задачи с названием %s не существует", title)));
    }

    @Override
    public List<Todo> findByStatus(Status status) {
        return todoRepository.findAllTodos().stream()
                .filter(o -> o.getStatus().equals(status))
                .toList();
    }

    @Override
    public List<Todo> sortByDeadline() {
        return todoRepository.findAllTodos().stream()
                .sorted((o1, o2) -> o1.getDeadline().isBefore(o2.getDeadline()) ? 1 : -1)
                .toList();
    }

    @Override
    public List<Todo> sortByStatus() {
        return todoRepository.findAllTodos().stream()
                .sorted(Comparator.comparing(Todo::getStatus))
                .toList();
    }
}
