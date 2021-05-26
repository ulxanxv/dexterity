package ru.dexterity.web.controllers.personalarea;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.dexterity.web.helper.FileHelper;
import ru.dexterity.web.helper.ModelHelper;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class PersonalAreaController {

    private final FileHelper fileHelper;
    private final ModelHelper modelHelper;

    @GetMapping("/personal_area")
    public String personalArea(Model model) {
        modelHelper.setCredential(model);
        modelHelper.setDecidedTaskList(model);

        return "personal_area";
    }

    @PostMapping("/file/upload")
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile, Model model) {
        try {
            fileHelper.uploadFile(multipartFile);

            modelHelper.setCredential(model);
            modelHelper.setDecidedTaskList(model);
        } catch (IOException e) {
            return "redirect:/personal_area";
        }

        return "redirect:/personal_area";
    }

}