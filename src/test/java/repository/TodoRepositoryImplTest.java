package repository;

import application.entity.Todo;
import application.enums.Status;
import application.exceptions.TodoAlreadyExistException;
import application.exceptions.TodoNotFoundException;
import application.repository.TodoRepository;
import application.repository.TodoRepositoryImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class TodoRepositoryImplTest {

    private TodoRepository repository = new TodoRepositoryImpl(new ArrayList<>());

    @BeforeEach
    public void setUp() {
        // Перед каждым тестом создаем новый пустой список для inMemoryStorage
        repository = new TodoRepositoryImpl(new ArrayList<>());
    }

    @Test
    public void testSaveTodo_success() {
        Todo todo = new Todo();
        todo.setTitle("Task1");
        todo.setDescription("Description1");
        todo.setStatus(Status.TODO);
        todo.setDeadline(LocalDateTime.now().plusDays(1));

        repository.saveTodo(todo);

        List<Todo> todos = repository.findAllTodos();
        assertEquals(1, todos.size());
        assertEquals("Task1", todos.get(0).getTitle());
    }

    @Test
    public void testSaveTodo_alreadyExists() {
        Todo todo1 = new Todo();
        todo1.setTitle("Task1");
        repository.saveTodo(todo1);

        Todo todo2 = new Todo();
        // Название такое же, проверка проводится с игнорированием регистра
        todo2.setTitle("task1");

        assertThrows(TodoAlreadyExistException.class, () -> repository.saveTodo(todo2));
    }

    @Test
    public void testRemoveTodo_success() {
        Todo todo = new Todo();
        todo.setTitle("Task1");
        repository.saveTodo(todo);

        repository.removeTodo(todo);
        assertTrue(repository.findAllTodos().isEmpty());
    }

    @Test
    public void testRemoveTodo_notFound() {
        Todo todo = new Todo();
        todo.setTitle("NonExistent");

        assertThrows(TodoNotFoundException.class, () -> repository.removeTodo(todo));
    }

    @Test
    public void testFindTodoByTitle_found() {
        Todo todo = new Todo();
        todo.setTitle("Task1");
        repository.saveTodo(todo);

        Optional<Todo> found = repository.findTodoByTitle("TASK1");
        assertTrue(found.isPresent());
        assertEquals("Task1", found.get().getTitle());
    }

    @Test
    public void testFindTodoByTitle_notFound() {
        Optional<Todo> found = repository.findTodoByTitle("Unknown");
        assertFalse(found.isPresent());
    }

    @Test
    public void testUpdateStatus_success() {
        Todo todo = new Todo();
        todo.setTitle("Task1");
        todo.setStatus(Status.TODO);
        repository.saveTodo(todo);

        repository.updateStatus("Task1", Status.DONE);
        Optional<Todo> updated = repository.findTodoByTitle("Task1");
        assertTrue(updated.isPresent());
        assertEquals(Status.DONE, updated.get().getStatus());
    }

    @Test
    public void testUpdateStatus_notFound() {
        assertThrows(TodoNotFoundException.class, () -> repository.updateStatus("NonExistent", Status.DONE));
    }

    @Test
    public void testUpdateDescription_success() {
        Todo todo = new Todo();
        todo.setTitle("Task1");
        todo.setDescription("Old description");
        repository.saveTodo(todo);

        repository.updateDescription("Task1", "New description");
        Optional<Todo> updated = repository.findTodoByTitle("Task1");
        assertTrue(updated.isPresent());
        assertEquals("New description", updated.get().getDescription());
    }

    @Test
    public void testUpdateDescription_notFound() {
        assertThrows(TodoNotFoundException.class, () -> repository.updateDescription("NonExistent", "New description"));
    }

    @Test
    public void testUpdateDeadline_success() {
        Todo todo = new Todo();
        todo.setTitle("Task1");
        LocalDateTime oldDeadline = LocalDateTime.now().plusDays(1);
        todo.setDeadline(oldDeadline);
        repository.saveTodo(todo);

        LocalDateTime newDeadline = LocalDateTime.now().plusDays(2);
        repository.updateDeadline("Task1", newDeadline);
        Optional<Todo> updated = repository.findTodoByTitle("Task1");
        assertTrue(updated.isPresent());
        assertEquals(newDeadline, updated.get().getDeadline());
    }

    @Test
    public void testUpdateDeadline_notFound() {
        assertThrows(TodoNotFoundException.class, () ->
                repository.updateDeadline("NonExistent", LocalDateTime.now().plusDays(3))
        );
    }
}
