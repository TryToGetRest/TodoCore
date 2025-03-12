package application.entity;

import application.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class Todo {
    private Integer id;
    private String title;
    private String description;
    private Status status;
    private LocalDateTime deadline;
}
