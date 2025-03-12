package application;

import application.controller.ConsoleTodoController;
import application.entity.Todo;
import application.repository.KeyValueTodoRepository;
import application.service.KeyValueTodoService;

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
                new KeyValueTodoService(new KeyValueTodoRepository(new HashMap<Integer, Todo>()))
        );
    }
}
