package ru.dexterity.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.dexterity.exception.AuthException;
import ru.dexterity.exception.AuthException.AuthError;
import ru.dexterity.dao.models.Credential;
import ru.dexterity.dao.repositories.CredentialRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthComponent {

    private final CredentialRepository credentialRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void registerUser(Credential credential) {
        if (Strings.isEmpty(credential.getLogin()) || Strings.isEmpty(credential.getPassword()) || Strings.isEmpty(credential.getEmail())) {
            throw new AuthException(AuthError.CREDENTIAL_INCORRECT, "Логин, E-Mail или пароль не заданы");
        }

        if (!credential.getPassword().equals(credential.getConfirmPassword())) {
            throw new AuthException(AuthError.CREDENTIAL_INCORRECT, "Пароли не совпадают");
        }

        if (credential.getLogin().length() < 3) {
            throw new AuthException(AuthError.CREDENTIAL_INCORRECT, "Логин должен быть не менее 3 символов");
        }

        if (credentialRepository.findByLogin(credential.getLogin()).isPresent()) {
            throw new AuthException(AuthError.CREDENTIAL_EXIST, "Такой пользователь уже существует");
        }

        if (credentialRepository.findByEmail(credential.getEmail()).isPresent()) {
            throw new AuthException(AuthError.CREDENTIAL_EXIST, "Пользователь с таким E-Mail уже существует");
        }

        credential.setRole("USER");
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        credential.setExperience(1000);

        try {
            credentialRepository.save(credential);
        } catch (Exception exception) {
            throw new AuthException(AuthError.CREDENTIAL_INCORRECT, "Данные некорректны");
        }
    }
}