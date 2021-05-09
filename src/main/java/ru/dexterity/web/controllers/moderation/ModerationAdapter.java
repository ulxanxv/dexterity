package ru.dexterity.web.controllers.moderation;

import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.dexterity.web.controllers.compile.CompilationInfoRequest;
import ru.dexterity.web.controllers.moderation.domain.TaskOwner;
import ru.dexterity.web.controllers.moderation.domain.UpdateTableResponse;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ModerationAdapter {

    private final RestTemplate restTemplate;

    @Value("${compileta.compileAllUrl}")
    private String compileAllUrl;

    public ModerationAdapter(RestTemplateBuilder builder) {
        RestTemplateBuilder templateBuilder = builder.rootUri(compileAllUrl)
            .setConnectTimeout(Duration.ofSeconds(10))
            .setReadTimeout(Duration.ofSeconds(30));

        this.restTemplate = templateBuilder.build();
    }

    public UpdateTableResponse updateRatingTable(Map<TaskOwner, CompilationInfoRequest> compilationInfoDetail) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<Map<TaskOwner, CompilationInfoRequest>> requestHttpEntity =
            new HttpEntity<>(compilationInfoDetail, httpHeaders);

        ResponseEntity<UpdateTableResponse> responseEntity =
            restTemplate.postForEntity(compileAllUrl, requestHttpEntity, UpdateTableResponse.class);

        return responseEntity.getBody();
    }

}
