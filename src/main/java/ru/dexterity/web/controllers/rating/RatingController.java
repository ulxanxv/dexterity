package ru.dexterity.web.controllers.rating;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.dexterity.dao.models.TaskRating;
import ru.dexterity.web.api.TaskComponent;
import ru.dexterity.web.helper.ModelHelper;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RatingController {

    private final ModelHelper modelHelper;
    private final TaskComponent taskComponent;

    @GetMapping("/rating")
    public String rating(Model model) {
        modelHelper.setCredential(model);
        modelHelper.setTaskList(model);

        return "rating";
    }

    @ResponseBody
    @GetMapping("/task_rating_list")
    public ResponseEntity<List<TaskRating>> taskRatingList(@RequestParam String shortDescription) {
        return ResponseEntity.ok(taskComponent.ratingList(shortDescription));
    }

}