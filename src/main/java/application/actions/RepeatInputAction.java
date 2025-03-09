package application.actions;

public class RepeatInputAction implements Action {

    @Override
    public void execute() {
        System.out.println("Повторите ввод, для вашего ввода не нашлось подходящей команды");
    }
}
