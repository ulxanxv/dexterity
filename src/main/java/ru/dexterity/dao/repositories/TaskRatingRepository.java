package ru.dexterity.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dexterity.dao.models.TaskRating;

import java.util.List;
import java.util.Optional;

public interface TaskRatingRepository extends JpaRepository<TaskRating, Long> {

    Optional<List<TaskRating>> findByCredentialId(Long credentialId);

}
