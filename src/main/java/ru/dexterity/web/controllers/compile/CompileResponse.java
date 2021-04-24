package ru.dexterity.web.controllers.compile;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompileResponse {

    private String status;
    private String message;

}
