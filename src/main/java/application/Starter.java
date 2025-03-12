package application;

import application.controller.ConsoleTodoController;

public class Starter {

    public static void main(String[] args) {
        ConsoleTodoController application = ConsoleTodoController.init();
        application.launch();
    }
}
