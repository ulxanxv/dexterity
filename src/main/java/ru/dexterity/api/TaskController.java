package ru.dexterity.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.dexterity.dao.models.Task;
import ru.dexterity.web.domain.SelectedTask;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskComponent taskComponent;
    private final SelectedTask selectedTask;

    @GetMapping("/task")
    public TaskResponse taskByShortDescription(@RequestParam(name = "short_description") String shortDescription) {
        try {
            Task task = taskComponent.findByShortDescription(shortDescription);
            selectedTask.setSelectedTask(task.getId());

            return new TaskResponse("ok", task);
        } catch (TaskNotFoundException exception) {
            return new TaskResponse("not_found");
        }
    }

    @GetMapping("/selected_task")
    public TaskResponse selectedUserTask() {
        return new TaskResponse("ok", taskComponent.findById(selectedTask.getSelectedTask()));
    }

    @JsonInclude(Include.NON_NULL)
    public static class TaskResponse {

        private final String status;
        private Task taskInfo;

        public TaskResponse(String status) {
            this.status = status;
        }

        public TaskResponse(String status, Task taskInfo) {
            this.status = status;
            this.taskInfo = taskInfo;
        }

        public String getStatus() {
            return status;
        }

        public Task getTaskInfo() {
            return taskInfo;
        }
    }

}
