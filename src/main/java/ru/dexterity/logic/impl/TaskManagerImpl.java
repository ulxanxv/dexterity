package ru.dexterity.logic.impl;

import liquibase.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.dexterity.dao.models.Task;
import ru.dexterity.dao.repositories.TaskRepository;
import ru.dexterity.exception.TaskErrorCode;
import ru.dexterity.exception.TaskNotFoundException;
import ru.dexterity.logic.TaskManager;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskManagerImpl implements TaskManager {

    private final TaskRepository taskRepository;

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task findByShortDescription(String shortDescription) {
        return taskRepository
                .findByShortDescription(shortDescription)
                .orElseThrow(() -> new TaskNotFoundException(TaskErrorCode.TASK_NOT_FOUND));
    }

    @Override
    public Task findById(Long id) {
        return taskRepository
                .findById(id)
                .orElseThrow(() -> new TaskNotFoundException(TaskErrorCode.TASK_NOT_FOUND));
    }

}
