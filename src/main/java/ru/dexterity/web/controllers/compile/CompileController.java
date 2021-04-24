package ru.dexterity.web.controllers.compile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CompileController {

    private final CompileComponent compileComponent;

    @GetMapping("/run_code")
    public ResponseEntity<CompileResponse> runCode(String code, String taskName) {
        return ResponseEntity.ok(compileComponent.runCode(code, taskName));
    }

}
