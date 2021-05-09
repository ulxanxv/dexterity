package ru.dexterity.web.controllers.moderation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dexterity.dao.models.Task;
import ru.dexterity.dao.models.TaskRating;
import ru.dexterity.dao.repositories.TaskRatingRepository;
import ru.dexterity.dao.repositories.TaskRepository;
import ru.dexterity.web.controllers.compile.CompilationInfoRequest;
import ru.dexterity.web.controllers.compile.CompileComponent;
import ru.dexterity.web.controllers.compile.CompileComponent.AverageTaskMetrics;
import ru.dexterity.web.controllers.compile.CompileResponse;
import ru.dexterity.web.controllers.moderation.domain.TaskOwner;
import ru.dexterity.web.controllers.moderation.domain.UpdateTableResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ModerationComponent {

    private final TaskRepository taskRepository;
    private final TaskRatingRepository taskRatingRepository;

    private final CompileComponent compileComponent;
    private final ModerationAdapter moderationAdapter;

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
        List<TaskRating> updatableList = taskRatingRepository.findAll();

        Map<Long, AverageTaskMetrics> metricsMap = compileComponent.averageTasksMetricsMap(
            updatableList.stream()
                .map(map -> map.getTask().getId())
                .collect(Collectors.toList())
        );

        Map<TaskOwner, CompilationInfoRequest> compilationInfoDetail = new HashMap<>();
        updatableList.forEach(each -> {
            TaskOwner taskOwner = new TaskOwner();
            taskOwner.setTaskId(each.getTask().getId());
            taskOwner.setCredentialId(each.getCredential().getId());

            CompilationInfoRequest compilationInfoRequest = CompilationInfoRequest.builder()
                .code(each.getSolution())
                .className(each.getTask().getClassName())
                .testCode(each.getTask().getTestCode())
                .testClassName(each.getTask().getTestClassName())
                .averageSpeed(metricsMap.get(each.getTask().getId()).getAverageSpeed())
                .averageBrevity(metricsMap.get(each.getTask().getId()).getAverageBrevity())
                .build();

            compilationInfoDetail.put(taskOwner, compilationInfoRequest);
        });

        Map<TaskOwner, CompileResponse> updatedTasks = moderationAdapter.updateRatingTable(compilationInfoDetail).getUpdatableList();
        updatableList.forEach(each -> {
            CompileResponse compileResponse = updatedTasks.get(
                new TaskOwner(each.getCredential().getId(), each.getTask().getId())
            );

            each.setBrevity(compileResponse.getBrevity());
            each.setRapidity(compileResponse.getRapidity());
            each.setTotalScore(compileResponse.getTotalScore());
        });

        taskRatingRepository.saveAll(updatableList);
    }

    public Task findById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.orElse(null);
    }

    public Task findByShortDescriptionAndInModerationTrue(String shortDescription) {
        return taskRepository.findByShortDescriptionAndInModerationTrue(shortDescription);
    }

}
