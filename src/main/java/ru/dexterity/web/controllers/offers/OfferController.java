package ru.dexterity.web.controllers.offers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.dexterity.dao.models.Task;
import ru.dexterity.web.helper.ModelHelper;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OfferController {

    private final ModelHelper modelHelper;
    private final OfferComponent offerComponent;

    @GetMapping("/offers")
    public String offers(Model model) {
        modelHelper.setCredential(model);
        return "offers";
    }

    @PostMapping("/offer_task")
    public ResponseEntity<?> offerTask(@RequestBody Task task) {
        try {
            // log.info(task.toString());
            offerComponent.saveModerationOffer(task);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.badRequest().body("такая задача уже существует");
        }
    }

}
