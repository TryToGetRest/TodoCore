package application.actions;

public class HelpAction implements Action {

    @Override
    public void execute() {
        System.out.println("Для того, что бы добавить задачу, вводи: \"add\"");
        System.out.println("Для того, что бы вывести все задачи, вводи: \"list\"");
        System.out.println("Для того, что бы получить задачи по статусу, вводи: \"filter\"");
        System.out.println("Для того, что бы удалить задачу, вводи: \"delete\"");
        System.out.println("Для того, что бы сортировать задачи, вводи: \"sort\"");
        System.out.println("Для того, что бы изменить задачу, вводи: \"edit\"");
        System.out.println("Для того, что бы выйти из приложения, вводи: \"exit\"");
    }
}
