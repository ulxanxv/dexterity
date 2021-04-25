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
public class ModerationController {

    private final ModelHelper modelHelper;

    @GetMapping("/moderation")
    public String moderation(Model model) {
        modelHelper.setCredential(model);
        return "moderation";
    }

    @GetMapping("/moderation_list")
    public String moderationList(Model model) {
        modelHelper.setCredential(model);
        modelHelper.setModerationTaskList(model);
        return "moderation_list";
    }

}
