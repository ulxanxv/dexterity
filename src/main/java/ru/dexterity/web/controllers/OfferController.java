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
public class OfferController {

    private final ModelHelper modelHelper;

    @GetMapping("/offers")
    public String offers(Model model) {
        modelHelper.setCredential(model);
        return "offers";
    }

}
