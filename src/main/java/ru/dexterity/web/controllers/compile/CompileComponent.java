package ru.dexterity.web.controllers.compile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.dexterity.web.api.TaskComponent;
import ru.dexterity.dao.models.Credential;
import ru.dexterity.dao.models.Task;
import ru.dexterity.dao.models.TaskRating;
import ru.dexterity.dao.repositories.CredentialRepository;
import ru.dexterity.dao.repositories.TaskRatingRepository;
import ru.dexterity.security.AuthorizationAttributes;

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

    private static final double DEFAULT_SPEED = 1_000_000_000D;
    private static final double DEFAULT_BREVITY = 200D;

    public CompileResponse runCode(String code, String taskName) {
        Task task = taskComponent.findByShortDescription(taskName);

        CompileResponse response;
        try {
            AverageTaskMetrics taskMetrics = this.averageTaskMetrics(task.getId());

            response = compileAdapter.runCode(
                code, task.getClassName(), task.getTestCode(), task.getTestClassName(), taskMetrics.getAverageSpeed(), taskMetrics.getAverageBrevity()
            );
        } catch (Exception ignored) {
            return new CompileResponse("error", "Cервер не доступен, попробуйте позже...");
        }

        if (response.getStatus().equals("ok")) {
            if (this.alreadyDecided(authorizationAttributes.getCredential().getId(), task.getId())) {
                response.setStatus("already_decided");
                response.setMessage(String.format(
                    "Вы уже выполняли данное задание, поэтому рейтинг-таблица не будет обновлена\n\nПоказатели по этому решению:\n\nСкорость: %s\nКраткость: %s\nОценка: %s\n",
                    response.getRapidity(),
                    response.getBrevity(),
                    response.getTotalScore()
                ));
                return response;
            }

            this.updateExperience(task.getDifficult(), response.getTotalScore());
            this.saveRating(response, task, code);
        }

        return response;
    }

    public Map<Long, AverageTaskMetrics> averageTasksMetricsMap(List<Long> ids) {
        Map<Long, AverageTaskMetrics> allTasksMetrics = new HashMap<>();
        ids.forEach(each -> {
            allTasksMetrics.put(each, this.averageTaskMetrics(each));
        });

        return allTasksMetrics;
    }

    public AverageTaskMetrics averageTaskMetrics(Long taskId) {
        List<TaskRating> taskRatings = taskRatingRepository.findByTaskId(taskId);
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

        return averageTaskMetrics;
    }

    private void updateExperience(int taskDifficult, double totalScore) {
        int fullExperience = ((int) (100 * taskDifficult + (100 * taskDifficult * totalScore / 100.0)));

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
        taskRating.setTotalScore(compileResponse.getTotalScore());

        taskRatingRepository.save(taskRating);
    }

    private boolean alreadyDecided(Long credentialId, Long taskId) {
        return taskRatingRepository.findByCredentialIdAndTaskId(credentialId, taskId).isPresent();
    }

    @Getter
    @Setter
    public static class AverageTaskMetrics {

        private double averageSpeed;
        private double averageBrevity;

    }

}
