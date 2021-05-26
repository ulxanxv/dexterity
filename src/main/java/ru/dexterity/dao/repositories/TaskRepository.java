package ru.dexterity.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.dexterity.dao.models.Task;
import ru.dexterity.dao.models.TaskRating;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByShortDescription(String shortDescription);

    List<Task> findAllByInModerationFalse();

    List<Task> findAllByInModerationTrue();

    Task findByShortDescriptionAndInModerationTrue(String shortDescription);

    @Query("SELECT t FROM Task t WHERE lower(t.shortDescription) LIKE %:query% AND t.inModeration = false")
    List<Task> findAllByQuery(String query);

}