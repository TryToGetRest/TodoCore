package repository;

import application.entity.Todo;
import application.enums.Status;
import application.exceptions.TodoNotFoundException;
import application.repository.InMemoryTodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TodoRepositoryUtils;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryRepositoryTest {

    private InMemoryTodoRepository repository;

    @BeforeEach
    public void setUp() {
        repository = TodoRepositoryUtils.createTodoRepository();
    }


    @Test
    @DisplayName("Check set up method functionality")
    public void givenSetUp_whenSetUp_thenStorageNotEmpty() {
        //given setUp method

        //when setUp method

        //then
        assertFalse(repository.findAllTodos().isEmpty());
    }

    @Test
    @DisplayName("Test save method functionality")
    public void givenTodo_whenSave_thenPersist() {
        //given
        String title = "Test Task";
        String description = "Test Description";
        Status status = Status.TODO;
        LocalDateTime deadline = LocalDateTime.now().plusDays(1);

        //when
        repository.saveTodo(title, description, status, deadline);
        Integer id = TodoRepositoryUtils.findIdByTitle(title, repository);
        //then
        Todo persisted = repository.findTodoById(id).orElse(null);

        assertNotNull(persisted, "Todo не найден по идентификатору");
        assertEquals(title, persisted.getTitle());
        assertEquals(description, persisted.getDescription());
        assertEquals(status, persisted.getStatus());
        assertEquals(deadline, persisted.getDeadline());
    }

    @Test
    @DisplayName("Test double remove todo functionality")
    public void givenPersistedTodo_whenDoubleRemove_thenException() {
        //given
        String title = "Test Task";
        repository.saveTodo(title, "Desc", Status.IN_PROGRESS, LocalDateTime.now().plusDays(1));

        //when
        Integer id = TodoRepositoryUtils.findIdByTitle(title, repository);
        repository.removeTodo(id);

        // then
        assertThrows(TodoNotFoundException.class, () -> repository.removeTodo(id));
    }

    @Test
    @DisplayName("Test update todo status")
    public void givenPersistedTodo_whenUpdateStatus_thenSuccess() {
        //given
        int id = repository.findAllTodos().entrySet().stream().findFirst().get().getValue().getId();
        Status status = repository.findTodoById(id).orElse(null).getStatus();

        //when
        repository.updateStatus(id, Status.DONE);
        Todo updated = repository.findTodoById(id).orElse(null);

        //then
        assertNotNull(updated);
        assertEquals(Status.DONE, updated.getStatus());
        assertNotEquals(status, updated.getStatus());
    }

    @Test
    @DisplayName("Test update todo status functionality")
    public void givenPersistedTodo_whenUpdateDescription_thenSuccess() {
        //given
        String title = "Task";
        repository.saveTodo(title, "Desc", Status.TODO, LocalDateTime.now().plusDays(1));
        String newDescription = "Updated Description";
        Integer id = TodoRepositoryUtils.findIdByTitle(title, repository);

        //when
        repository.updateDescription(id, newDescription);
        Todo updated = repository.findTodoById(id).orElse(null);

        //then
        assertNotNull(updated);
        assertEquals(newDescription, updated.getDescription());
    }

    @Test
    @DisplayName("Test update todo title functionality")
    public void givenPersistedTodo_whenUpdateTitle_thenSuccess() {
        //given
        String title = "Task";
        repository.saveTodo(title, "Desc", Status.TODO, LocalDateTime.now().plusDays(1));
        Integer id = TodoRepositoryUtils.findIdByTitle(title, repository);
        String newTitle = "Updated title";

        //when
        repository.updateTitle(id, newTitle);
        Todo updated = repository.findTodoById(id).orElse(null);

        //then
        assertNotNull(updated);
        assertEquals(newTitle, updated.getTitle());
    }

    @Test
    @DisplayName("Test update todo deadline functionality")
    public void givenPersistedTodo_whenUpdateDeadline_thenSuccess() {
        //given
        String title = "Task";
        repository.saveTodo(title, "Desc", Status.TODO, LocalDateTime.now().plusDays(1));
        Integer id = TodoRepositoryUtils.findIdByTitle(title, repository);
        LocalDateTime newDeadline = LocalDateTime.now().plusDays(2);

        //when
        repository.updateDeadline(id, newDeadline);
        Todo updated = repository.findTodoById(id).orElse(null);

        //then
        assertNotNull(updated);
        assertEquals(newDeadline, updated.getDeadline());
    }
}
