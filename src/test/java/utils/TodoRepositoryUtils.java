package utils;

import application.enums.Status;
import application.exceptions.TodoNotFoundException;
import application.repository.InMemoryTodoRepository;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

public class TodoRepositoryUtils {

    public static InMemoryTodoRepository createTodoRepository() {
        InMemoryTodoRepository inMemoryTodoRepository = new InMemoryTodoRepository(new ConcurrentHashMap<>());
        inMemoryTodoRepository.saveTodo("test1", "some description", Status.TODO, LocalDateTime.now().plusMonths(2));
        inMemoryTodoRepository.saveTodo("test2", "", Status.IN_PROGRESS, LocalDateTime.now().plusDays(2));
        inMemoryTodoRepository.saveTodo("test3", "some description larger", Status.DONE, LocalDateTime.now().minusMonths(1));
        inMemoryTodoRepository.saveTodo("test4", "hello world", Status.IN_PROGRESS, LocalDateTime.now().plusHours(3));
        inMemoryTodoRepository.saveTodo("go to work", "faster!!!", Status.IN_PROGRESS, LocalDateTime.now().plusMinutes(2));
        inMemoryTodoRepository.saveTodo("take offer", "dude just do it", Status.TODO, LocalDateTime.now().plusMonths(2));
        inMemoryTodoRepository.saveTodo("go to shop", "milk, butter, etc", Status.DONE, LocalDateTime.now().minusHours(45));
        return inMemoryTodoRepository;
    }


    public static int findIdByTitle(String title, InMemoryTodoRepository repository) {
        return repository.findAllTodos().entrySet()
                .stream()
                .filter(entry -> entry.getValue().getTitle().equals(title))
                .findFirst().orElseThrow(() -> new TodoNotFoundException("todo not found exception"))
                .getValue().getId();
    }
}
