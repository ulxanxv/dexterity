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
        Object principal = SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();

        if (principal.toString().equals("anonymousUser")) {
            return null;
        }

        return credentialRepository.findByLogin(((User) principal).getUsername()).orElse(null);
    }

    public String getRole() {
        Credential credential = this.getCredential();
        return credential != null ? credential.getRole() : "NONE";
    }

}
