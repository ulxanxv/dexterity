package ru.dexterity.web.controllers.compile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.dexterity.api.TaskComponent;
import ru.dexterity.dao.models.Credential;
import ru.dexterity.dao.models.Task;
import ru.dexterity.dao.models.TaskRating;
import ru.dexterity.dao.repositories.CredentialRepository;
import ru.dexterity.dao.repositories.TaskRatingRepository;
import ru.dexterity.security.AuthorizationAttributes;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompileComponent {

    private final TaskComponent taskComponent;
    private final CompileAdapter compileAdapter;
    private final AuthorizationAttributes authorizationAttributes;

    private final CredentialRepository credentialRepository;
    private final TaskRatingRepository taskRatingRepository;

    public CompileResponse runCode(String code, String taskName) {
        Task task = taskComponent.findByShortDescription(taskName);

        CompileResponse response;
        try {
            response = compileAdapter.runCode(
                code, task.getClassName(), task.getTestCode(), task.getTestClassName()
            );
        } catch (Exception ignored) {
            return new CompileResponse("error", "сервер не доступен, попробуйте позже");
        }

        if (response.getStatus().equals("ok")) {
            if (this.alreadyDecided(authorizationAttributes.getCredential().getId(), task.getId()))
                return response;

            this.updateExperience(task.getDifficult());
            this.saveRating(response, task, code);
        }

        return response;
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
        taskRating.setTotalScore(0.0D); // FIXME

        taskRatingRepository.save(taskRating);
    }

    private boolean alreadyDecided(Long credentialId, Long taskId) {
        return taskRatingRepository.findByCredentialIdAndTaskId(credentialId, taskId).isPresent();
    }

}
