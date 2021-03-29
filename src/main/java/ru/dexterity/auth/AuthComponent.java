package ru.dexterity.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.dexterity.dao.models.Credential;
import ru.dexterity.dao.repositories.CredentialRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthComponent {

    private final CredentialRepository credentialRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Credential registerUser(Credential credential) {
        if (credential == null) {
            log.error("USER IS NULL");
            return null;
        }

        credential.setRole("USER");
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        return credentialRepository.save(credential);
    }
}
