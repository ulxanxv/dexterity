package ru.dexterity.web.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.dexterity.exception.SelectedTaskNotFound;
import ru.dexterity.web.component.IndexComponent;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class IndexControllerAdvice {

    private final IndexComponent indexComponent;

    @ExceptionHandler(SelectedTaskNotFound.class)
    public ModelAndView handleSelectedTaskNotFound(SelectedTaskNotFound ex) {
        logError(ex);

        RedirectView rv = new RedirectView("/");
        rv.setExposeModelAttributes(false);

        ModelAndView mav = new ModelAndView(rv);

        indexComponent.setTaskList(mav);
        indexComponent.setAuthorize(mav);

        mav.addObject("taskError", "error");
        return mav;
    }

    private void logError(RuntimeException ex) {
        log.error(ex.getMessage());
    }

}
