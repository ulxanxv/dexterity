package ru.dexterity.web.component;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import ru.dexterity.web.service.TaskService;

@Component
@RequiredArgsConstructor
public class IndexComponent {

    private final TaskService taskService;

    public void setAuthorize(ModelAndView modelAndView) {
        Object user;

        if ((user = SecurityContextHolder.getContext().getAuthentication().getPrincipal()).toString().equals("anonymousUser")) {
            modelAndView.addObject("user", user);
        } else {
            modelAndView.addObject("user", ((User) user).getUsername());
        }
    }

    public void setTaskList(ModelAndView modelAndView) {
        modelAndView.addObject("taskList", taskService.findAll());
    }

}
