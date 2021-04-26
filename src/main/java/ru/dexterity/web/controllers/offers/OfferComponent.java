package ru.dexterity.web.controllers.offers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dexterity.dao.models.Task;
import ru.dexterity.dao.repositories.TaskRepository;

@Component
@AllArgsConstructor
public class OfferComponent {

    private final TaskRepository taskRepository;

    public void saveModerationOffer(Task task) {
        task.setInModeration(true);
        taskRepository.save(task);
    }

}
