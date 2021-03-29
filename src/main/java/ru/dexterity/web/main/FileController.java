package ru.dexterity.web.main;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class FileController {

    private final FileComponent fileComponent;

    @GetMapping("/file/upload")
    public String uploadFilePage() {
        return "parea";
    }

    @PostMapping("/file/upload")
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        try {
            fileComponent.uploadFile(multipartFile);
        } catch (IOException e) {
            return "redirect:/file/upload";
        }
        return "redirect:/";
    }

}
