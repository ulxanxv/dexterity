package ru.dexterity.web.controllers.moderation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import ru.dexterity.dao.models.Task;
import ru.dexterity.security.AuthorizationAttributes;
import ru.dexterity.web.domain.ModerationTask;
import ru.dexterity.web.helper.ModelHelper;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ModerationController {

    private final ModelHelper modelHelper;
    private final ModerationTask moderationTask;
    private final ModerationComponent moderationComponent;
    private final AuthorizationAttributes authorizationAttributes;

    @GetMapping("/moderation")
    public String moderation(Model model) {
        if (!authorizationAttributes.getRole().equals("MODER")) {
            return "redirect:/";
        }

        if (moderationTask.getModerationTask() == null) {
            return "redirect:/moderation_list";
        }

        modelHelper.setCredential(model);
        model.addAttribute("moderationTask", moderationComponent.findById(moderationTask.getModerationTask()));
        return "moderation";
    }

    @GetMapping("/moderation_list")
    public String moderationList(Model model) {
        if (!authorizationAttributes.getRole().equals("MODER")) {
            return "redirect:/";
        }

        modelHelper.setCredential(model);
        modelHelper.setModerationTaskList(model);
        return "moderation_list";
    }

    @ResponseBody
    @PostMapping("/accept")
    public ResponseEntity<?> acceptTask(@RequestBody Task editedTask) {
        if (!authorizationAttributes.getRole().equals("MODER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        moderationComponent.acceptTask(moderationTask.getModerationTask(), editedTask);
        moderationTask.setModerationTask(null);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ResponseBody
    @PostMapping("/decline")
    public ResponseEntity<?> declineTask() {
        if (!authorizationAttributes.getRole().equals("MODER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        moderationComponent.declineTask(moderationTask.getModerationTask());
        moderationTask.setModerationTask(null);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ResponseBody
    @PostMapping("/select_moderation_task")
    public ResponseEntity<?> selectModeration(@RequestParam String shortDescription) {
        if (!authorizationAttributes.getRole().equals("MODER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        moderationTask.setModerationTask(
            moderationComponent.findByShortDescriptionAndInModerationTrue(shortDescription).getId()
        );

        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @PatchMapping("/update_rating_table")
    public ResponseEntity<?> updateRatingTable() {
        if (!authorizationAttributes.getRole().equals("MODER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        try {
            moderationComponent.updateRatingTable();
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).build();
        }
    }

}
