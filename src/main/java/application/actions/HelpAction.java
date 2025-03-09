package application.actions;

public class HelpAction implements Action {

    @Override
    public void execute() {
        System.out.println("Для того, что бы добавить задачу, вводи: \"add\"");
        System.out.println("Для того, что бы вывести все задачи, вводи: \"list\"");
        System.out.println("Для того, что бы получить задачи по статусу, вводи: \"filter\"");
        System.out.println("Для того, что бы удалить задачу, вводи: \"delete\"");
        System.out.println("Для того, что бы сортировать задачи по дедлайну, вводи: \"sort by deadline\"");
        System.out.println("Для того, что бы сортировать задачи по статусу, вводи: \"sort by status\"");
        System.out.println("Для того, что бы изменить дедлайн задачи, вводи: \"edit deadline\"");
        System.out.println("Для того, что бы изменить описание задачи, вводи: \"edit description\"");
        System.out.println("Для того, что бы изменить статус задачи, вводи: \"edit status\"");
        System.out.println("Для того, что бы изменить название задачи, вводи: \"edit title\"");
        System.out.println("Для того, что бы выйти из приложения, вводи: \"exit\"");
    }
}
