package ru.dexterity.web.controllers.index;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.dexterity.web.helper.ModelHelper;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final ModelHelper modelHelper;

    @GetMapping("/")
    public String index(Model model) {
        modelHelper.setCredential(model);
        modelHelper.setTaskList(model);

        return "index";
    }

}

