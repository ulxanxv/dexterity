package ru.dexterity.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.dexterity.api.manager.TaskComponent;
import ru.dexterity.dao.models.Task;
import ru.dexterity.web.domain.SelectedTask;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskComponent taskComponent;

    @Autowired
    private SelectedTask selectedTask;

    @GetMapping("/task")
    @ResponseStatus(HttpStatus.OK)
    public Task taskByShortDescription(@RequestParam(name = "short_description") String shortDescription) {
        Task task = taskComponent.findByShortDescription(shortDescription);
        selectedTask.setSelectedTask(task.getId());
        return task;
    }

}
