package ru.dexterity.web.main;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.dexterity.dao.models.Credential;
import ru.dexterity.dao.repositories.CredentialRepository;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileComponent {

    @Value("${upload.path}")
    private String uploadPath;

    private final CredentialRepository credentialRepository;

    public void uploadFile(MultipartFile multipartFile) throws IOException {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Credential credential = credentialRepository.findByLogin(username).orElse(null);

        if (multipartFile != null && credential != null) {
            if (!Strings.isEmpty(credential.getFileName())) {
                new File(uploadPath + credential.getFileName()).delete();
                log.info("FILE DELETED");
            }

            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + multipartFile.getOriginalFilename();

            BufferedOutputStream stream =
                new BufferedOutputStream(new FileOutputStream(new File(uploadPath + resultFilename)));
            stream.write(multipartFile.getBytes());
            stream.close();

            credential.setFileName(resultFilename);
            credential.setImage(multipartFile.getBytes());
            credentialRepository.save(credential);
        }
    }

}
