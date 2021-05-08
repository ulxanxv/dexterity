package ru.dexterity.web.controllers.compile;

import lombok.Data;

@Data
public class CompileResponse {

    public CompileResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    private String status;
    private String message;

    private int brevity;
    private double rapidity;
    private double totalScore;

}
