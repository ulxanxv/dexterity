package ru.dexterity.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.dexterity.dao.models.TaskRating;

import java.util.List;
import java.util.Optional;

public interface TaskRatingRepository extends JpaRepository<TaskRating, Long> {

    Optional<List<TaskRating>> findByCredentialId(Long credentialId);

    @Query("SELECT tr FROM TaskRating tr WHERE tr.task.shortDescription = ?1 ORDER BY tr.totalScore")
    List<TaskRating> findRatingListByShortDescription(String shortDescription);

    @Query("SELECT tr FROM TaskRating tr WHERE tr.credential.id = :credentialId AND tr.task.id = :taskId")
    Optional<TaskRating> findByCredentialIdAndTaskId(Long credentialId, Long taskId);

    List<TaskRating> findByTaskId(Long taskId);

}
