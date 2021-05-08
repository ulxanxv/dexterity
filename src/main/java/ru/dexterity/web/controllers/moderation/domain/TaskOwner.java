package ru.dexterity.web.controllers.moderation.domain;

import lombok.Data;

@Data
public class TaskOwner {

    private Long credentialId;
    private Long taskId;

}
