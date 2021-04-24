package ru.dexterity.web.controllers.compile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dexterity.api.TaskComponent;
import ru.dexterity.dao.models.Credential;
import ru.dexterity.dao.models.Task;
import ru.dexterity.dao.repositories.CredentialRepository;
import ru.dexterity.security.AuthorizationAttributes;

@Component
@RequiredArgsConstructor
public class CompileComponent {

    private final TaskComponent taskComponent;
    private final CompileAdapter compileAdapter;
    private final CredentialRepository credentialRepository;
    private final AuthorizationAttributes authorizationAttributes;

    public CompileResponse runCode(String code, String taskName) {
        Task task = taskComponent.findByShortDescription(
            taskName
        );

        try {
            CompileResponse response = compileAdapter.runCode(
                code, task.getClassName(), task.getTestCode(), task.getTestClassName()
            );

            if (response.getStatus().equals("ok")) {
                Credential credential = authorizationAttributes.getCredential();

            }

            return response;
        } catch (Exception ignored) {
            return new CompileResponse("error", "server unavailable, please try later");
        }
    }

}
