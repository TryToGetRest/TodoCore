package application;

import application.controller.ConsoleTodoController;
import application.entity.Todo;
import application.repository.InMemoryTodoRepository;
import application.service.InMemoryTodoService;

import java.util.HashMap;
import java.util.Scanner;

public class Starter {

    public static void main(String[] args) {
        ConsoleTodoController application = init();
        application.launch();
    }


    public static ConsoleTodoController init() {
        return new ConsoleTodoController(
                new Scanner(System.in),
                new InMemoryTodoService(new InMemoryTodoRepository(new HashMap<Integer, Todo>()))
        );
    }
}
