package application;

import application.controller.TodoController;
import application.controller.TodoControllerImpl;

public class Starter {

    public static void main(String[] args) {
        TodoController application = TodoControllerImpl.init();
        application.execute();
    }
}
