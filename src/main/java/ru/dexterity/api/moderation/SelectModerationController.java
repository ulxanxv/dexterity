package ru.dexterity.api.moderation;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.dexterity.web.domain.ModerationTask;

@RestController
@AllArgsConstructor
public class SelectModerationController {

    private final ModerationTask moderationTask;

    @PostMapping("/select_moderation")
    public ResponseEntity<?> selectModeration(@RequestParam String taskShortDescription) {
        return ResponseEntity.ok().build();
    }

}
