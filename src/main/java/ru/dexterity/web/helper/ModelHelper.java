package ru.dexterity.web.helper;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import ru.dexterity.api.TaskComponent;
import ru.dexterity.dao.models.Credential;
import ru.dexterity.security.AuthorizationAttributes;

@Component
@RequiredArgsConstructor
public class ModelHelper {

    private final TaskComponent taskComponent;
    private final AuthorizationAttributes authorizationAttributes;

    public void setCredential(Model model) {
        Credential credential = authorizationAttributes.getCredential();

        if (credential != null) {
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
            model.addAttribute("username", "anonymousUser");
        }
    }

    public void setTaskList(Model model) {
        model.addAttribute("taskList", taskComponent.findAll());
    }

}
