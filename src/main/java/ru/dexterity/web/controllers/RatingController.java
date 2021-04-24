package ru.dexterity.web.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.dexterity.web.helper.ModelHelper;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RatingController {

    private final ModelHelper modelHelper;

    @GetMapping("/rating")
    public String rating(Model model) {
        modelHelper.setCredential(model);
        modelHelper.setTaskList(model);
        return "rating";
    }

}
