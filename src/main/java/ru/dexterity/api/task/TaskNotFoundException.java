package ru.dexterity.api.task;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskNotFoundException extends RuntimeException {

    private TaskErrorCode errorCode;

    public enum TaskErrorCode {
        TASK_NOT_FOUND
    }

}
