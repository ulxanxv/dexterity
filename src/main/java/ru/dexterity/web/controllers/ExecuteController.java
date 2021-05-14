package ru.dexterity.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.dexterity.api.TaskComponent;
import ru.dexterity.web.domain.SelectedTask;
import ru.dexterity.web.helper.ModelHelper;

@Controller
@RequiredArgsConstructor
public class ExecuteController {

    private final ModelHelper modelHelper;
    private final SelectedTask selectedTask;
    private final TaskComponent taskComponent;

    @GetMapping("/execute")
    public String execute(Model model) {
        modelHelper.setCredential(model);

        if (selectedTask.getSelectedTask() != null) {
            model.addAttribute("selectedTask", taskComponent.findById(selectedTask.getSelectedTask()));
            return "execute";
        }

        return "redirect:/";
    }

}
