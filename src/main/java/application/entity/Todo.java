package application.entity;

import application.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Todo {

    private String title;
    private String description;
    private Status status;
    private LocalDateTime deadline;

    @Override
    public String toString() {
        return "Задача{" +
                "название='" + title + '\'' +
                ", описание='" + description + '\'' +
                ", статус=" + status.toString() +
                ", дедлайн=" + deadline +
                '}';
    }
}
