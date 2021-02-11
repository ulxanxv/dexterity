package ru.dexterity.web.service;

import ru.dexterity.dao.models.Task;

import java.util.List;

public interface TaskService {

    List<Task> findAll();

    Task findByShortDescription(String shortDescription);

    Task findById(Long id);

}
