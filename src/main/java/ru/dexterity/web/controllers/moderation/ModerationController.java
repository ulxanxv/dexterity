package ru.dexterity.web.controllers.moderation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.dexterity.web.domain.ModerationTask;
import ru.dexterity.web.helper.ModelHelper;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ModerationController {

    private final ModelHelper modelHelper;
    private final ModerationTask moderationTask;
    private final ModerationComponent moderationComponent;

    @GetMapping("/moderation")
    public String moderation(Model model) {
        modelHelper.setCredential(model);
        model.addAttribute("moderationTask", moderationComponent.findById(moderationTask.getModerationTask()));
        return "moderation";
    }

    @GetMapping("/moderation_list")
    public String moderationList(Model model) {
        modelHelper.setCredential(model);
        modelHelper.setModerationTaskList(model);
        return "moderation_list";
    }

    @ResponseBody
    @PostMapping("/select_moderation")
    public ResponseEntity<?> selectModeration(@RequestParam String shortDescription) {
        moderationTask.setModerationTask(
            moderationComponent.findByShortDescriptionAndInModerationTrue(shortDescription).getId()
        );

        return ResponseEntity.ok().build();
    }

}
