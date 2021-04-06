package ru.dexterity.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.dexterity.api.TaskNotFoundException.TaskErrorCode;
import ru.dexterity.dao.models.Task;
import ru.dexterity.dao.repositories.TaskRepository;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskComponent {

    private final TaskRepository taskRepository;

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task findByShortDescription(String shortDescription) {
        return taskRepository
                .findByShortDescription(shortDescription)
                .orElseThrow(() -> new TaskNotFoundException(TaskErrorCode.TASK_NOT_FOUND));
    }

    public Task findById(Long id) {
        return taskRepository
                .findById(id)
                .orElseThrow(() -> new TaskNotFoundException(TaskErrorCode.TASK_NOT_FOUND));
    }

}
