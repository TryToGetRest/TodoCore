package service;

import application.entity.Todo;
import application.enums.Status;
import application.enums.TodoFields;
import application.exceptions.ActionNotFoundException;
import application.exceptions.TodoNotFoundException;
import application.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TodoRepositoryUtils;
import utils.TodoServiceUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TodoServiceTest {

    private TodoService service;

    @BeforeEach
    public void setUp() {
        service = new TodoService(TodoRepositoryUtils.createTodoRepository());
    }

    @Test
    @DisplayName("Test save todo functionality")
    public void givenNewTodo_whenSave_thenPersisted() {
        // given
        String title = "Test Task";
        String description = "Test Description";
        Status status = Status.TODO;
        LocalDateTime deadline = LocalDateTime.now().plusDays(1);

        // when
        service.saveTodo(title, description, status, deadline);

        // then
        Todo persisted = service.findTodoById(TodoServiceUtils.findIdByTitle(title, service));
        assertNotNull(persisted, "Задача должна быть сохранена");
        assertEquals(status, persisted.getStatus());
        assertEquals(deadline, persisted.getDeadline());
    }

    @Test
    @DisplayName("Test update todo status functionality")
    public void givenPersistedTodo_whenUpdateStatus_thenSuccess() {
        // given
        Todo saved = service.findAllTodos().entrySet().stream().findFirst().get().getValue();
        Status savedStatus = saved.getStatus();
        Status newStatus = (saved.getStatus() == Status.TODO) ? Status.DONE : Status.TODO;

        // when
        service.updateStatus(saved.getId(), newStatus);

        // then
        Todo updated = service.findTodoById(saved.getId());
        assertNotNull(updated);
        assertEquals(newStatus, updated.getStatus());
        assertNotEquals(savedStatus, updated.getStatus());
    }

    @Test
    @DisplayName("Test update todo description functionality")
    public void givenPersistedTodo_whenUpdateDescription_thenSuccess() {
        // given
        Todo saved = service.findAllTodos().entrySet().stream().findFirst().get().getValue();
        String savedDescription = saved.getDescription();
        String newDescription = "Updated test Description";

        // when
        service.updateDescription(saved.getId(), newDescription);

        // then
        Todo updated = service.findTodoById(saved.getId());
        assertNotNull(updated);
        assertEquals(newDescription, updated.getDescription());
        assertNotEquals(savedDescription, updated.getDescription());
    }

    @Test
    @DisplayName("Test update todo title functionality")
    public void givenPersistedTodo_whenUpdateTitle_thenSuccess() {
        // given
        Todo saved = service.findAllTodos().entrySet().stream().findFirst().get().getValue();
        String savedTitle = saved.getTitle();
        String newTitle = "new test title";

        // when
        service.updateTitle(saved.getId(), newTitle);

        // then
        Todo updated = service.findTodoById(saved.getId());
        assertNotNull(updated);
        assertEquals(newTitle, updated.getTitle());
        assertNotEquals(savedTitle, updated.getTitle());
    }

    @Test
    @DisplayName("Test update todo deadline functionality")
    public void givenPersistedTodo_whenUpdateDeadline_thenSuccess() {
        // given
        Todo saved = service.findAllTodos().entrySet().stream().findFirst().get().getValue();
        LocalDateTime savedDeadline = saved.getDeadline();
        LocalDateTime newDeadline = LocalDateTime.now().plusDays(1);

        // when
        service.updateDeadline(saved.getId(), newDeadline);

        // then
        Todo updated = service.findTodoById(saved.getId());
        assertNotNull(updated);
        assertEquals(newDeadline, updated.getDeadline());
        assertNotEquals(savedDeadline, updated.getDeadline());
    }

    @Test
    @DisplayName("Test remove todo functionality")
    public void givenPersistedTodo_whenRemove_thenNotFound() {
        // given
        String title = "Task";
        service.saveTodo(title, "Desc", Status.TODO, LocalDateTime.now().plusDays(1));
        Integer id = TodoServiceUtils.findIdByTitle(title, service);

        // when
        service.removeTodo(id);

        // then - попытка найти задачу приводит к выбрасыванию исключения
        assertThrows(TodoNotFoundException.class, () -> service.findTodoById(id));
    }

    @Test
    @DisplayName("Test filter todos functionality")
    public void givenMultipleTodos_whenFindByStatus_thenCorrect() {
        // given
        TodoServiceUtils.dropTodos(service);
        service.saveTodo("Task1", "Desc1", Status.TODO, LocalDateTime.now().plusDays(1));
        service.saveTodo("Task2", "Desc2", Status.DONE, LocalDateTime.now().plusDays(2));
        service.saveTodo("Task3", "Desc3", Status.IN_PROGRESS, LocalDateTime.now().plusDays(3));

        // when
        Map<Integer, Todo> todosByStatus = service.findByStatus(Status.TODO);

        // then
        todosByStatus.forEach((id, todo) -> assertEquals(Status.TODO, todo.getStatus()));
    }

    @Test
    @DisplayName("Test ordering by deadline functionality")
    public void givenMultipleTodos_whenSortByDeadline_thenSorted() {
        // given
        TodoServiceUtils.dropTodos(service);
        service.saveTodo("TaskA", "DescA", Status.TODO, LocalDateTime.now().plusDays(5));
        service.saveTodo("TaskB", "DescB", Status.TODO, LocalDateTime.now().plusDays(1));
        service.saveTodo("TaskC", "DescC", Status.TODO, LocalDateTime.now().plusDays(3));

        // when
        List<Todo> sorted = service.sortBy(TodoFields.DEADLINE);

        // then
        assertFalse(sorted.isEmpty());
        for (int i = 0; i < sorted.size() - 1; i++) {
            assertTrue(sorted.get(i).getDeadline().isBefore(sorted.get(i + 1).getDeadline())
                    || sorted.get(i).getDeadline().isEqual(sorted.get(i + 1).getDeadline()));
        }
    }

    @Test
    @DisplayName("Test ordering by status functionality")
    public void givenMultipleTodos_whenSortByStatus_thenSorted() {
        // given
        TodoServiceUtils.dropTodos(service);
        service.saveTodo("TaskA", "DescA", Status.IN_PROGRESS, LocalDateTime.now().plusDays(5));
        service.saveTodo("TaskB", "DescB", Status.TODO, LocalDateTime.now().plusDays(1));
        service.saveTodo("TaskC", "DescC", Status.DONE, LocalDateTime.now().plusDays(3));

        // when
        List<Todo> sorted = service.sortBy(TodoFields.STATUS);

        // then
        assertFalse(sorted.isEmpty());
        for (int i = 0; i < sorted.size() - 1; i++) {
            assertTrue(sorted.get(i).getStatus().ordinal() <= sorted.get(i + 1).getStatus().ordinal());
        }
    }

    @Test
    @DisplayName("Test ordering by incorrect field functionality")
    public void givenInvalidField_whenSortBy_thenThrowException() {
        // when-then - сортировка по не поддерживаемому полю (например, TITLE) должна выбрасывать исключение
        assertThrows(ActionNotFoundException.class, () -> service.sortBy(TodoFields.TITLE));
    }
}
