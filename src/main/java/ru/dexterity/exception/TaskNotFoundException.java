package ru.dexterity.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TaskNotFoundException extends RuntimeException {

    private final TaskErrorCode errorCode;

    public enum TaskErrorCode {
        TASK_NOT_FOUND
    }

}
