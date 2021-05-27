package ru.dexterity.web.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.dexterity.exception.TaskNotFoundException;
import ru.dexterity.exception.TaskNotFoundException.TaskErrorCode;
import ru.dexterity.dao.models.Task;
import ru.dexterity.dao.models.TaskRating;
import ru.dexterity.dao.repositories.TaskRatingRepository;
import ru.dexterity.dao.repositories.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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

    public List<Task> findAllByQuery(String query, Integer difficult, Boolean showDeleted) {
        List<Task> allByQuery = taskRepository.findAllByQuery(query.toLowerCase(Locale.ROOT));
        if (difficult != null && difficult != 0) {
            allByQuery = allByQuery.stream()
                .filter(each -> each.getDifficult() == difficult)
                .collect(Collectors.toList());
        }

        if (!showDeleted) {
            allByQuery = allByQuery.stream()
                .filter(each -> !each.isDeleted())
                .collect(Collectors.toList());
        }

        return allByQuery;
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

    public void deleteTask(Task task, String reason, Boolean completeRemoval) {
        if (completeRemoval) {
            taskRatingRepository.deleteAll(taskRatingRepository.findByTaskId(task.getId()));
            taskRepository.delete(task);
            return;
        }

        task.setDeleted(true);
        task.setDeletionReason(reason);
        taskRepository.save(task);
    }

}
