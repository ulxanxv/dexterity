package ru.dexterity.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.dexterity.dao.models.Task;
import ru.dexterity.dao.models.TaskRating;
import ru.dexterity.exception.TaskNotFoundException;
import ru.dexterity.web.domain.SelectedTask;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskComponent taskComponent;
    private final SelectedTask selectedTask;

    @GetMapping("/search_tasks")
    public ResponseEntity<List<Task>> searchTasks(@RequestParam String query,
                                                  @RequestParam(required = false) Integer difficult
    ) {
        return ResponseEntity.ok(taskComponent.findAllByQuery(query, difficult));
    }

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

    @GetMapping("/task_rating_list")
    public ResponseEntity<List<TaskRating>> taskRatingList(@RequestParam String shortDescription) {
        return ResponseEntity.ok(taskComponent.ratingList(shortDescription));
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
