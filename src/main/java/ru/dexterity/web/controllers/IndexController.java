package ru.dexterity.web.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.dexterity.web.helper.ModelHelper;

import java.io.File;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

    private final ModelHelper modelHelper;

    @Value("${upload.imagesPath}")
    private String uploadPath;

    @GetMapping("/")
    public String index(Model model) {
        modelHelper.setCredential(model);
        modelHelper.setTaskList(model);
        return "index";
    }

}

