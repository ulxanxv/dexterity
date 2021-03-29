package ru.dexterity.web.main;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.dexterity.exception.SelectedTaskNotFound;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class MainControllerAdvice {

    private final MainComponent indexComponent;

    @ExceptionHandler(SelectedTaskNotFound.class)
    public ModelAndView handleSelectedTaskNotFound(SelectedTaskNotFound ex) {
        logError(ex);

        RedirectView rv = new RedirectView("/");
        rv.setExposeModelAttributes(false);

        ModelAndView mav = new ModelAndView(rv);
        indexComponent.setTaskList(mav);
        indexComponent.setUsernameAndPhoto(mav);

        return mav;
    }

    private void logError(RuntimeException ex) {
        log.error("Error :: {}", ex.toString());
    }

}
