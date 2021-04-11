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

            return new TaskResponse(
                "ok", task.getShortDescription(), task.getLongDescription(), task.getDifficult()
            );
        } catch (TaskNotFoundException exception) {
            return new TaskResponse("not_found");
        }
    }

    @JsonInclude(Include.NON_NULL)
    public static class TaskResponse {

        private final String status;

        private String shortDescription;
        private String longDescription;
        private int difficult;

        public TaskResponse(String status) {
            this.status = status;
        }

        public TaskResponse(String status, String shortDescription, String longDescription, int difficult) {
            this.status = status;
            this.shortDescription = shortDescription;
            this.longDescription = longDescription;
            this.difficult = difficult;
        }

        public String getStatus() {
            return status;
        }

        public String getShortDescription() {
            return shortDescription;
        }

        public String getLongDescription() {
            return longDescription;
        }

        public int getDifficult() {
            return difficult;
        }
    }

}
