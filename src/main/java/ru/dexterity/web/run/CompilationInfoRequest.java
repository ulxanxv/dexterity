package ru.dexterity.web.run;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompilationInfoRequest {

    private String code;
    private String className;
    private String testCode;
    private String testClassName;

}
