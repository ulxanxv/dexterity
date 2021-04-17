package ru.dexterity.web.attach;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.dexterity.web.main.IndexComponent;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class FileController {

    private final FileComponent fileComponent;
    private final IndexComponent indexComponent;

    @GetMapping("/file/upload")
    public String uploadFilePage(Model model) {
        indexComponent.setUsernameAndPhoto(model);
        return "personal_area";
    }

    @PostMapping("/file/upload")
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile, Model model) {
        try {
            fileComponent.uploadFile(multipartFile);
            indexComponent.setUsernameAndPhoto(model);
        } catch (IOException e) {
            return "redirect:/file/upload";
        }
        return "redirect:/file/upload";
    }

}
