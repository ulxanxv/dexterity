package ru.dexterity.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RunCodeController {

    @GetMapping("/run_code")
    public RunCodeResponse runCode(String code, String taskName) {
        log.info("{} {}", code, taskName);
        return RunCodeResponse.OK;
    }

    @Data
    @AllArgsConstructor
    private static class RunCodeResponse {

        public static final RunCodeResponse OK = new RunCodeResponse("ok");
        public static final RunCodeResponse FAIL = new RunCodeResponse("fail");

        private String result;

    }

}
