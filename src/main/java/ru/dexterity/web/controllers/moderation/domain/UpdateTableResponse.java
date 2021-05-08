package ru.dexterity.web.controllers.moderation.domain;

import lombok.Data;
import ru.dexterity.web.controllers.compile.CompileResponse;

import java.util.Map;

@Data
public class UpdateTableResponse {

    private Map<TaskOwner, CompileResponse> updatableList;

}
