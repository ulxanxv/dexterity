package ru.dexterity.web.run;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompilationInfoRequest {

    private String className;
    private String testClassName;
    private String methodName;
    private String code;
    private String testCode;
    private String solutionCode;
    private String solutionMethodName;

}
