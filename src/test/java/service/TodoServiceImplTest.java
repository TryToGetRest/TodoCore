package service;

import application.entity.Todo;
import application.enums.Status;
import application.exceptions.TodoNotFoundException;
import application.repository.TodoRepository;
import application.service.TodoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TodoServiceImplTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoServiceImpl todoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateStatus_success() {
        // Given
        String title = "Task1";
        Status newStatus = Status.DONE;
        doNothing().when(todoRepository).updateStatus(title, newStatus);

        // When
        todoService.updateStatus(title, newStatus);

        // Then: проверяем, что вызван метод репозитория
        verify(todoRepository, times(1)).updateStatus(title, newStatus);
    }

    @Test
    public void testUpdateDescription_success() {
        // Given
        String title = "Task1";
        String newDescription = "Updated description";
        doNothing().when(todoRepository).updateDescription(title, newDescription);

        // When
        todoService.updateDescription(title, newDescription);

        // Then
        verify(todoRepository, times(1)).updateDescription(title, newDescription);
    }

    @Test
    public void testUpdateDeadline_success() {
        // Given
        String title = "Task1";
        LocalDateTime newDeadline = LocalDateTime.now().plusDays(5);
        doNothing().when(todoRepository).updateDeadline(title, newDeadline);

        // When
        todoService.updateDeadline(title, newDeadline);

        // Then
        verify(todoRepository, times(1)).updateDeadline(title, newDeadline);
    }

    @Test
    public void testSaveTodo_success() {
        // Given
        Todo todo = new Todo();
        todo.setTitle("Task1");
        doNothing().when(todoRepository).saveTodo(todo);

        // When
        todoService.saveTodo(todo);

        // Then
        verify(todoRepository, times(1)).saveTodo(todo);
    }

    @Test
    public void testRemoveTodo_success() {
        // Given
        Todo todo = new Todo();
        todo.setTitle("Task1");
        doNothing().when(todoRepository).removeTodo(todo);

        // When
        todoService.removeTodo(todo);

        // Then
        verify(todoRepository, times(1)).removeTodo(todo);
    }

    @Test
    public void testFindTodoByTitle_success() {
        // Given
        String title = "Task1";
        Todo todo = new Todo();
        todo.setTitle(title);
        when(todoRepository.findTodoByTitle(title)).thenReturn(Optional.of(todo));

        // When
        Todo result = todoService.findTodoByTitle(title);

        // Then
        assertNotNull(result);
        assertEquals(title, result.getTitle());
        verify(todoRepository, times(1)).findTodoByTitle(title);
    }

    @Test
    public void testFindTodoByTitle_notFound() {
        // Given
        String title = "NonExistingTask";
        when(todoRepository.findTodoByTitle(title)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(TodoNotFoundException.class, () -> todoService.findTodoByTitle(title));
        verify(todoRepository, times(1)).findTodoByTitle(title);
    }

    @Test
    public void testFindByStatus() {
        // Given
        Status status = Status.DONE;
        Todo todo1 = new Todo();
        todo1.setTitle("Task1");
        todo1.setStatus(Status.DONE);

        Todo todo2 = new Todo();
        todo2.setTitle("Task2");
        todo2.setStatus(Status.TODO);

        Todo todo3 = new Todo();
        todo3.setTitle("Task3");
        todo3.setStatus(Status.DONE);

        List<Todo> allTodos = Arrays.asList(todo1, todo2, todo3);
        when(todoRepository.findAllTodos()).thenReturn(allTodos);

        // When
        List<Todo> result = todoService.findByStatus(status);

        // Then
        assertEquals(2, result.size());
        assertTrue(result.contains(todo1));
        assertTrue(result.contains(todo3));
    }

    @Test
    public void testSortByDeadline() {
        // Given
        // Метод sortByDeadline сортирует по убыванию (если o1.deadline раньше, то возвращается 1)
        Todo todo1 = new Todo();
        todo1.setTitle("Task1");
        todo1.setDeadline(LocalDateTime.of(2023, 5, 1, 0, 0));

        Todo todo2 = new Todo();
        todo2.setTitle("Task2");
        todo2.setDeadline(LocalDateTime.of(2023, 5, 3, 0, 0));

        Todo todo3 = new Todo();
        todo3.setTitle("Task3");
        todo3.setDeadline(LocalDateTime.of(2023, 5, 2, 0, 0));

        List<Todo> allTodos = Arrays.asList(todo1, todo2, todo3);
        when(todoRepository.findAllTodos()).thenReturn(allTodos);

        // When
        List<Todo> result = todoService.sortByDeadline();

        // Then: самый поздний deadline должен идти первым
        assertEquals("Task2", result.get(0).getTitle());
        assertEquals("Task3", result.get(1).getTitle());
        assertEquals("Task1", result.get(2).getTitle());
    }

    @Test
    public void testSortByStatus() {

        Todo todo1 = new Todo();
        todo1.setTitle("Task1");
        todo1.setStatus(Status.DONE);

        Todo todo2 = new Todo();
        todo2.setTitle("Task2");
        todo2.setStatus(Status.TODO);

        Todo todo3 = new Todo();
        todo3.setTitle("Task3");
        todo3.setStatus(Status.DONE);

        List<Todo> allTodos = Arrays.asList(todo1, todo2, todo3);
        when(todoRepository.findAllTodos()).thenReturn(allTodos);

        // When
        List<Todo> result = todoService.sortByStatus();

        assertEquals("Task2", result.get(0).getTitle());
        // Остальные задачи должны иметь статус DONE
        assertEquals(Status.DONE, result.get(1).getStatus());
        assertEquals(Status.DONE, result.get(2).getStatus());
    }
}
