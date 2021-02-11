package ru.dexterity.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dexterity.dao.models.Credential;

import java.util.Optional;

public interface CredentialRepository extends JpaRepository<Credential, Long> {

    Optional<Credential> findByLogin(String login);

}

