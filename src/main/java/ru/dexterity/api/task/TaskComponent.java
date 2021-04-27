package ru.dexterity.api.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.dexterity.api.task.TaskNotFoundException.TaskErrorCode;
import ru.dexterity.dao.models.Task;
import ru.dexterity.dao.models.TaskRating;
import ru.dexterity.dao.repositories.TaskRatingRepository;
import ru.dexterity.dao.repositories.TaskRepository;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskComponent {

    private final TaskRepository taskRepository;
    private final TaskRatingRepository taskRatingRepository;

    public List<Task> findAllNotModeration() {
        return taskRepository.findAllByInModerationFalse();
    }

    public List<Task> findAllModeration() {
        return taskRepository.findAllByInModerationTrue();
    }

    public List<TaskRating> ratingList(String shortDescription) {
        return taskRatingRepository.findRatingListByShortDescription(shortDescription);
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
