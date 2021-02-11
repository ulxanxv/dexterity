package ru.dexterity.web.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dexterity.dao.models.Task;
import ru.dexterity.logic.TaskManager;
import ru.dexterity.web.service.TaskService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskManager taskManager;

    @Override
    public List<Task> findAll() {
        return taskManager.findAll();
    }

    @Override
    public Task findByShortDescription(String shortDescription) {
        return taskManager.findByShortDescription(shortDescription);
    }

    @Override
    public Task findById(Long id) {
        return taskManager.findById(id);
    }

}
