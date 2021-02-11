package ru.dexterity.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.dexterity.dao.models.Task;
import ru.dexterity.web.service.TaskService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController("/v1")
@RequiredArgsConstructor
@SessionAttributes(types = Task.class)
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/task")
    @ResponseStatus(HttpStatus.OK)
    public Task taskByShortDescription(HttpServletResponse response,
                                       @RequestParam(name = "shortDescription") String shortDescription
    ) {
        Task task = taskService.findByShortDescription(
                shortDescription
        );

        Cookie selectedTask = new Cookie("SELECTED_TASK", task.getId().toString());
        response.addCookie(selectedTask);

        return task;
    }

}
