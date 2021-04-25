package ru.dexterity.web.controllers.compile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Collections;

@Service
public class CompileAdapter {

    private final RestTemplate restTemplate;

    @Value("${compileta.url}")
    private String compiletaUrl;

    public CompileAdapter(RestTemplateBuilder builder) {
        RestTemplateBuilder templateBuilder = builder.rootUri(compiletaUrl)
            .setConnectTimeout(Duration.ofSeconds(10))
            .setReadTimeout(Duration.ofSeconds(30));

        this.restTemplate = templateBuilder.build();
    }

    public CompileResponse runCode(String code,  String className, String testCode, String testClassName) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        CompilationInfoRequest compilationInfoRequest = CompilationInfoRequest.builder()
            .code(code)
            .className(className)
            .testCode(testCode)
            .testClassName(testClassName)
            .build();

        HttpEntity<CompilationInfoRequest> requestHttpEntity = new HttpEntity<>(compilationInfoRequest, httpHeaders);

        ResponseEntity<CompileResponse> responseEntity = restTemplate.postForEntity(compiletaUrl, requestHttpEntity, CompileResponse.class);
        return responseEntity.getBody();
    }

}