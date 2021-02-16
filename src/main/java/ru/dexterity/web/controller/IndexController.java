package ru.dexterity.web.controller;

import liquibase.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.dexterity.exception.SelectedTaskNotFound;
import ru.dexterity.web.component.IndexComponent;
import ru.dexterity.web.service.TaskService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

    private final TaskService taskService;
    private final IndexComponent indexComponent;

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("index");

        indexComponent.setAuthorize(mav);
        indexComponent.setTaskList(mav);

        return mav;
    }

    @GetMapping("/execute")
    public ModelAndView execute(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("execute");
        Cookie cookie = Arrays.stream(request.getCookies())
                .filter(x -> StringUtils.equalsIgnoreCaseAndEmpty(x.getName(), "SELECTED_TASK"))
                .findFirst()
                .orElseThrow(SelectedTaskNotFound::new);

        indexComponent.setAuthorize(mav);
        mav.addObject("selectedTask", taskService.findById(Long.parseLong(cookie.getValue())));

        return mav;
    }

    @GetMapping("/login")
    public String login() {
        return "log_in";
    }

    @GetMapping("/sign")
    public String signIn() {
        return "sign_in";
    }

}

