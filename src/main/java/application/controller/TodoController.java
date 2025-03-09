package application.controller;

import application.actions.Action;

import java.io.IOException;

public interface TodoController {

    void sayHello();

    void execute();

    Action inputProcessing(String input) throws IOException;
}
