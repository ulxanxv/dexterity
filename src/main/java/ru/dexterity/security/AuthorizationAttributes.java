package ru.dexterity.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import ru.dexterity.dao.models.Credential;
import ru.dexterity.dao.repositories.CredentialRepository;

@Component
@RequiredArgsConstructor
public class AuthorizationAttributes {

    private final CredentialRepository credentialRepository;

    public Credential getCredential() {
        String username = ((User) SecurityContextHolder.
            getContext().
            getAuthentication().
            getPrincipal()
        ).getUsername();

        return credentialRepository.findByLogin(username).orElse(null);
    }

}
