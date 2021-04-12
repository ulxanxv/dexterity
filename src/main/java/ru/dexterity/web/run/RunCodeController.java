package ru.dexterity.web.run;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dexterity.dao.models.Task;
import ru.dexterity.dao.repositories.TaskRepository;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.Random;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RunCodeController {

    private final RunCodeComponent runCodeComponent;

    @GetMapping("/run_code")
    public RunCodeResponse runCode(String code, String taskName) {
        String runCode = runCodeComponent.runCode(code, taskName);
        log.info("RESPONSE :: {}", runCode);
        return new RunCodeResponse(runCode);
    }

    @Data
    @AllArgsConstructor
    private static class RunCodeResponse {

        public static final RunCodeResponse OK = new RunCodeResponse("ok");
        public static final RunCodeResponse FAIL = new RunCodeResponse("fail");

        private String result;

    }

}