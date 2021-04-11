package ru.dexterity.web.main;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.dexterity.api.TaskComponent;
import ru.dexterity.web.domain.SelectedTask;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

    private final TaskComponent taskComponent;
    private final IndexComponent indexComponent;
    private final SelectedTask selectedTask;

    @GetMapping("/")
    public String index(Model model) {
        indexComponent.setUsernameAndPhoto(model);
        indexComponent.setTaskList(model);

        return "index";
    }

    @GetMapping("/execute")
    public String execute(Model model) {
        indexComponent.setUsernameAndPhoto(model);

        if (selectedTask.getSelectedTask() != null) {
            model.addAttribute("selectedTask", taskComponent.findById(selectedTask.getSelectedTask()));
            return "execute";
        }

        return "redirect:/";
    }

}

