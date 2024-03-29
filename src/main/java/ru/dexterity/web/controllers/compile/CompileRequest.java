package ru.dexterity.web.controllers.compile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompileRequest {

    private String code;
    private String className;
    private String testCode;
    private String testClassName;

    private Double averageSpeed;
    private Double averageBrevity;

}
