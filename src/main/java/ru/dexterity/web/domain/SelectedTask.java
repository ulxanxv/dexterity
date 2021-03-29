package ru.dexterity.web.domain;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Data
@Component
@SessionScope
public class SelectedTask {
    private Long selectedTask;
}
