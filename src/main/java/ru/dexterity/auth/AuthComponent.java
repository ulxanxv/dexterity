package ru.dexterity.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.dexterity.auth.AuthException.AuthError;
import ru.dexterity.dao.models.Credential;
import ru.dexterity.dao.repositories.CredentialRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthComponent {

    private final CredentialRepository credentialRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void registerUser(Credential credential) {
        if (Strings.isEmpty(credential.getLogin()) || Strings.isEmpty(credential.getPassword())) {
            throw new AuthException(AuthError.CREDENTIAL_INCORRECT, "Данные некорректны");
        }

        if (credentialRepository.findByLogin(credential.getLogin()).isPresent()) {
            throw new AuthException(AuthError.CREDENTIAL_EXIST, "Такой пользователь уже существует");
        }

        if ((credential.getEmail() != null && credentialRepository.findByEmail(credential.getEmail()).isPresent())) {
            throw new AuthException(AuthError.CREDENTIAL_EXIST, "Пользователь с таким E-Mail уже существует");
        }

        credential.setRole("USER");
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));

        try {
            credentialRepository.save(credential);
        } catch (Exception exception) {
            throw new AuthException(AuthError.CREDENTIAL_INCORRECT, "Данные некорректны");
        }
    }
}