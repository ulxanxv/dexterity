package ru.dexterity.logic;

import ru.dexterity.dao.models.Task;

import java.util.List;

public interface TaskManager {

    List<Task> findAll();

    Task findByShortDescription(String shortDescription);

    Task findById(Long id);

}
