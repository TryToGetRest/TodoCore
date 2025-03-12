package application.enums;

public enum Status {

    TODO(1),
    IN_PROGRESS(2),
    DONE(3);

    Status(int priority) {
        this.priority = priority;
    }

    public final int priority;
}
