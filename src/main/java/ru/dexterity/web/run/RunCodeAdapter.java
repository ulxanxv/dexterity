package ru.dexterity.web.run;

import liquibase.pro.packaged.C;
import lombok.AllArgsConstructor;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RunCodeAdapter {

    private final RestTemplate restTemplate;

    @Value("${compileta.url}")
    private String compiletaUrl;

    public RunCodeAdapter(RestTemplateBuilder builder) {
        RestTemplateBuilder templateBuilder = builder.rootUri(compiletaUrl)
            .setConnectTimeout(Duration.ofSeconds(10))
            .setReadTimeout(Duration.ofSeconds(30));

        this.restTemplate = templateBuilder.build();
    }

    public String runCode(String code,  String className, String testCode, String testClassName) {
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

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(compiletaUrl, requestHttpEntity, String.class);
        return responseEntity.getBody();
    }

}
