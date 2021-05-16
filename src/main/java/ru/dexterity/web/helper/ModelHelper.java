package ru.dexterity.web.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import ru.dexterity.web.api.TaskComponent;
import ru.dexterity.dao.models.Credential;
import ru.dexterity.dao.models.TaskRating;
import ru.dexterity.dao.repositories.TaskRatingRepository;
import ru.dexterity.security.AuthorizationAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ModelHelper {

    private final TaskComponent taskComponent;
    private final AuthorizationAttributes authorizationAttributes;

    private final TaskRatingRepository taskRatingRepository;

    public void setCredential(Model model) {
        Credential credential = authorizationAttributes.getCredential();

        if (credential != null) {
            model.addAttribute("userExist", true);
            model.addAttribute("username", credential.getLogin());
            model.addAttribute("role", credential.getRole());
            model.addAttribute("experience", credential.getExperience());
            model.addAttribute("level", credential.getExperience() / 1000);
            if (!Strings.isEmpty(credential.getFileName())) {
                model.addAttribute("filename", credential.getFileName());
            } else {
                model.addAttribute("filename", "anon.jpg");
            }
        } else {
            model.addAttribute("userExist", false);
            model.addAttribute("username", "");
        }
    }

    public void setDecidedTaskList(Model model) {
        model.addAttribute("decidedTaskList", this.decidedTaskList());
    }

    public void setTaskList(Model model) {
        model.addAttribute("taskList", taskComponent.findAllNotModeration());
    }

    public void setModerationTaskList(Model model) {
        model.addAttribute("moderationTaskList", taskComponent.findAllModeration());
    }

    private List<TaskRating> decidedTaskList() {
        Optional<List<TaskRating>> optionalRatingList = taskRatingRepository.findByCredentialId(
            authorizationAttributes.getCredential().getId()
        );

        return optionalRatingList.orElse(Collections.emptyList());
    }

}
