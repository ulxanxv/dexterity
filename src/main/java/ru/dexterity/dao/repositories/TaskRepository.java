package ru.dexterity.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.dexterity.dao.models.Task;
import ru.dexterity.dao.models.TaskRating;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByShortDescription(String shortDescription);

    List<Task> findAllByInModerationFalse();

    List<Task> findAllByInModerationTrue();

    @Query("SELECT tr FROM TaskRating tr WHERE tr.task.shortDescription = ?1")
    List<TaskRating> findRatingListByShortDescription(String shortDescription);

}
