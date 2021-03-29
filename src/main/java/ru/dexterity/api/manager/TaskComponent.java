package ru.dexterity.api.manager;

import ru.dexterity.dao.models.Task;

import java.util.List;

public interface TaskComponent {

    List<Task> findAll();

    Task findByShortDescription(String shortDescription);

    Task findById(Long id);

}
