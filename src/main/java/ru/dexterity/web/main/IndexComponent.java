package ru.dexterity.web.main;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import ru.dexterity.api.TaskComponent;
import ru.dexterity.dao.models.Credential;
import ru.dexterity.dao.repositories.CredentialRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IndexComponent {

    private final TaskComponent taskComponent;
    private final CredentialRepository credentialRepository;

    public void setUsernameAndPhoto(ModelAndView modelAndView) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal.toString().equals("anonymousUser")) {
            modelAndView.addObject("username", principal);
        } else {
            String username = ((User) principal).getUsername();
            modelAndView.addObject("username", username);

            Optional<Credential> credential = credentialRepository.findByLogin(username);
            credential.ifPresent(value -> {
                if (!Strings.isEmpty(value.getFileName())) {
                    modelAndView.addObject("filename", value.getFileName());
                } else {
                    modelAndView.addObject("filename", "anon.jpg");
                }
            });
        }
    }

    public void setTaskList(ModelAndView modelAndView) {
        modelAndView.addObject("taskList", taskComponent.findAll());
    }

}
