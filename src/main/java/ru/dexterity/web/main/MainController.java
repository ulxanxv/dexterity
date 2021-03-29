package ru.dexterity.web.main;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.dexterity.api.manager.TaskComponent;
import ru.dexterity.web.domain.SelectedTask;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final TaskComponent taskComponent;
    private final MainComponent mainComponent;

    @Autowired
    private SelectedTask selectedTask;

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("index");

        mainComponent.setUsernameAndPhoto(mav);
        mainComponent.setTaskList(mav);

        return mav;
    }

    @GetMapping("/execute")
    public ModelAndView execute() {
        ModelAndView mav = new ModelAndView("execute");
        mainComponent.setUsernameAndPhoto(mav);

        mav.addObject("selectedTask", taskComponent.findById(selectedTask.getSelectedTask()));
        return mav;
    }

}

