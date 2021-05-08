package ru.dexterity.web.controllers.compile;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.util.Precision;
import org.springframework.stereotype.Component;
import ru.dexterity.api.TaskComponent;
import ru.dexterity.dao.models.Credential;
import ru.dexterity.dao.models.Task;
import ru.dexterity.dao.models.TaskRating;
import ru.dexterity.dao.repositories.CredentialRepository;
import ru.dexterity.dao.repositories.TaskRatingRepository;
import ru.dexterity.security.AuthorizationAttributes;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompileComponent {

    private final TaskComponent taskComponent;
    private final CompileAdapter compileAdapter;
    private final AuthorizationAttributes authorizationAttributes;

    private final CredentialRepository credentialRepository;
    private final TaskRatingRepository taskRatingRepository;

    private final Map<Long, AverageTaskMetrics> cacheMetrics = new HashMap<>();

    private static final double DEFAULT_SPEED = 1_000_000_000D;
    private static final double DEFAULT_BREVITY = 200D;

    public CompileResponse runCode(String code, String taskName) {
        Task task = taskComponent.findByShortDescription(taskName);

        CompileResponse response;
        try {
            AverageTaskMetrics taskMetrics = this.getAverageTaskMetricsFromCache(task);

            response = compileAdapter.runCode(
                code, task.getClassName(), task.getTestCode(), task.getTestClassName(), taskMetrics.getAverageSpeed(), taskMetrics.getAverageBrevity()
            );
        } catch (Exception ignored) {
            return new CompileResponse("error", "Cервер не доступен, попробуйте позже...");
        }

        if (response.getStatus().equals("ok")) {
            if (this.alreadyDecided(authorizationAttributes.getCredential().getId(), task.getId()))
                return response;

            this.updateExperience(task.getDifficult());
            this.saveRating(response, task, code);
        }

        return response;
    }

    private AverageTaskMetrics getAverageTaskMetricsFromCache(Task task) {

        if (cacheMetrics.containsKey(task.getId())) {
            return cacheMetrics.get(task.getId());
        }

        List<TaskRating> taskRatings = taskRatingRepository.findByTaskId(task.getId());
        double averageSpeed = taskRatings.stream()
            .map(TaskRating::getRapidity)
            .mapToDouble(Double::valueOf)
            .average()
            .orElse(DEFAULT_SPEED);

        double averageBrevity = taskRatings.stream()
            .map(TaskRating::getBrevity)
            .mapToInt(Integer::valueOf)
            .average()
            .orElse(DEFAULT_BREVITY);

        AverageTaskMetrics averageTaskMetrics = new AverageTaskMetrics();
        averageTaskMetrics.setAverageSpeed(averageSpeed);
        averageTaskMetrics.setAverageBrevity(averageBrevity);

        cacheMetrics.put(task.getId(), averageTaskMetrics);

        return averageTaskMetrics;
    }

    private void updateExperience(int taskDifficult) {
        int fullExperience = 100 * taskDifficult;

        Credential credential = authorizationAttributes.getCredential();
        credential.setExperience(
            credential.getExperience() + (fullExperience - fullExperience / 100 * credential.getExperience() / 1000)
        );

        credentialRepository.save(credential);
    }

    private void saveRating(CompileResponse compileResponse, Task completedTask, String userSolution) {
        TaskRating taskRating = new TaskRating();

        taskRating.setCredential(authorizationAttributes.getCredential());
        taskRating.setTask(completedTask);
        taskRating.setSolution(userSolution);
        taskRating.setBrevity(compileResponse.getBrevity());
        taskRating.setRapidity(compileResponse.getRapidity());
        taskRating.setTotalScore(
            Precision.round(compileResponse.getTotalScore(), 2)
        );

        taskRatingRepository.save(taskRating);
    }

    private boolean alreadyDecided(Long credentialId, Long taskId) {
        return taskRatingRepository.findByCredentialIdAndTaskId(credentialId, taskId).isPresent();
    }

    @Getter
    @Setter
    private static class AverageTaskMetrics {

        private double averageSpeed;
        private double averageBrevity;

    }

}
