package ru.dexterity.web.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.dexterity.dao.models.Credential;
import ru.dexterity.dao.repositories.CredentialRepository;
import ru.dexterity.security.AuthorizationAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileHelper {

    @Value("${upload.images-path}")
    private String uploadPath;

    private final CredentialRepository credentialRepository;
    private final AuthorizationAttributes authorizationAttributes;

    public void uploadFile(MultipartFile file) throws IOException {
        Credential credential = authorizationAttributes.getCredential();

        if (file != null && credential != null) {

            if (!Strings.isEmpty(credential.getFileName())) {
                Files.deleteIfExists(Paths.get(uploadPath + credential.getFileName()));
            }

            Path path = Paths.get(uploadPath);
            if (Files.notExists(path)) {
                Files.createDirectory(path);
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + file.getOriginalFilename();
            Files.write(Paths.get(uploadPath + resultFilename), file.getBytes());

            credential.setFileName(resultFilename);
            credentialRepository.save(credential);
        }
    }

}
