package ru.dexterity.web.controllers.moderation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dexterity.dao.models.Task;
import ru.dexterity.dao.repositories.TaskRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ModerationComponent {

    private final TaskRepository taskRepository;

    public Task findById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.orElse(null);
    }

    public Task findByShortDescriptionAndInModerationTrue(String shortDescription) {
        return taskRepository.findByShortDescriptionAndInModerationTrue(shortDescription);
    }

}
