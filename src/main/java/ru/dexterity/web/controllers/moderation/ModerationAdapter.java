package ru.dexterity.web.controllers.moderation;

import aj.org.objectweb.asm.TypeReference;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.dexterity.dao.models.Task;
import ru.dexterity.dao.models.TaskRating;
import ru.dexterity.web.controllers.compile.CompilationInfoRequest;
import ru.dexterity.web.controllers.compile.CompileResponse;
import ru.dexterity.web.controllers.moderation.domain.TaskOwner;
import ru.dexterity.web.controllers.moderation.domain.UpdateTableResponse;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
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

    public UpdateTableResponse updateRatingTable(List<TaskRating> updatableList) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Map<TaskOwner, CompilationInfoRequest> compilationInfoDetail = new HashMap<>();
        updatableList.forEach(each -> {
            TaskOwner taskOwner = new TaskOwner();
            taskOwner.setTaskId(each.getTask().getId());
            taskOwner.setCredentialId(each.getCredential().getId());

            CompilationInfoRequest compilationInfoRequest = CompilationInfoRequest.builder()
                .code(each.getSolution())
                .className(each.getTask().getClassName())
                .testCode(each.getTask().getTestCode())
                .testClassName(each.getTask().getTestClassName())
                .build();

            compilationInfoDetail.put(taskOwner, compilationInfoRequest);
        });


        HttpEntity<Map<TaskOwner, CompilationInfoRequest>> requestHttpEntity =
            new HttpEntity<>(compilationInfoDetail, httpHeaders);

        ResponseEntity<UpdateTableResponse> responseEntity =
            restTemplate.postForEntity(compileAllUrl, requestHttpEntity, UpdateTableResponse.class);

        return responseEntity.getBody();
    }

}
