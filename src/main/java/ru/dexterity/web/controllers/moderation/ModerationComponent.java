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

    public void acceptTask(Long taskId, Task editedTask) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            return;
        }

        task.setShortDescription(editedTask.getShortDescription());
        task.setLongDescription(editedTask.getLongDescription());
        task.setStartCode(editedTask.getStartCode());
        task.setTestCode(editedTask.getTestCode());
        task.setDifficult(editedTask.getDifficult());
        task.setClassName(editedTask.getClassName());
        task.setTestClassName(editedTask.getTestClassName());
        task.setInModeration(false);
        taskRepository.save(task);
    }

    public void declineTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            return;
        }

        taskRepository.delete(task);
    }

    public void updateRatingTable() {

    }

    public Task findById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.orElse(null);
    }

    public Task findByShortDescriptionAndInModerationTrue(String shortDescription) {
        return taskRepository.findByShortDescriptionAndInModerationTrue(shortDescription);
    }

}
