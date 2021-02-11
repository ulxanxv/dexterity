package ru.dexterity.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskNotFoundException extends RuntimeException {

    private TaskErrorCode errorCode;

}
