package ru.dexterity.web.controllers.moderation.domain;

import lombok.Getter;
import ru.dexterity.web.controllers.compile.CompileResponse;

import java.util.Map;

@Getter
public class UpdateTableResponse {

    private Map<TaskOwner, CompileResponse> updatableList;

}
