package ru.dexterity.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dexterity.dao.models.Credential;

import java.util.Optional;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {

    Optional<Credential> findByLogin(String login);

    Optional<Credential> findByEmail(String email);

}