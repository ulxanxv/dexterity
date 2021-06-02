package ru.dexterity.web.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.dexterity.dao.models.Task;
import ru.dexterity.dao.models.TaskRating;
import ru.dexterity.exception.TaskNotFoundException;
import ru.dexterity.security.AuthorizationAttributes;
import ru.dexterity.web.domain.SelectedTask;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskComponent taskComponent;
    private final SelectedTask selectedTask;
    private final AuthorizationAttributes authorizationAttributes;

    @GetMapping("/search_tasks")
    public ResponseEntity<List<Task>> searchTasks(@RequestParam String query,
                                                  @RequestParam(required = false) Integer difficult,
                                                  @RequestParam(required = false) Boolean showDeleted
    ) {
        return ResponseEntity.ok(taskComponent.findAllByQuery(query, difficult, showDeleted));
    }

    @GetMapping("/select_task")
    public TaskResponse selectTask(@RequestParam(name = "short_description") String shortDescription) {
        try {
            Task task = taskComponent.findByShortDescription(shortDescription);

            if (task.isDeleted()) {
                return new TaskResponse("deleted", task);
            }

            selectedTask.setSelectedTask(task.getId());
            return new TaskResponse("ok", task);
        } catch (TaskNotFoundException exception) {
            return new TaskResponse("not_found");
        }
    }

    @DeleteMapping("/delete_task")
    public TaskResponse deleteTask(@RequestParam String reason, @RequestParam Boolean completeRemoval) {
        if (!authorizationAttributes.getRole().equals("MODER")) {
            return new TaskResponse("not_enough_rights");
        }
        
        try {
            Task task =
                taskComponent.findById(selectedTask.getSelectedTask());

            taskComponent.deleteTask(
                task, reason, completeRemoval
            );

            selectedTask.setSelectedTask(null);
            return new TaskResponse("ok", task);
        } catch (TaskNotFoundException exception) {
            return new TaskResponse("not_found");
        }
    }

    @GetMapping("/selected_task")
    public TaskResponse getSelectedTask() {
        return new TaskResponse("ok", taskComponent.findById(selectedTask.getSelectedTask()));
    }

    @Getter
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

    }

}