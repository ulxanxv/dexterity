package ru.dexterity.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dexterity.dao.models.Task;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByShortDescription(String shortDescription);

}
