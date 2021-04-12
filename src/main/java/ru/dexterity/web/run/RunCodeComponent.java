package ru.dexterity.web.run;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dexterity.api.TaskComponent;
import ru.dexterity.dao.models.Task;

@Component
@RequiredArgsConstructor
public class RunCodeComponent {

    private final TaskComponent taskComponent;
    private final RunCodeAdapter runCodeAdapter;

    public String runCode(String code, String taskName) {
        Task task = taskComponent.findByShortDescription(
            taskName
        );
        return runCodeAdapter.runCode(code, task.getClassName(), task.getTestCode(), task.getTestClassName());
    }

}
