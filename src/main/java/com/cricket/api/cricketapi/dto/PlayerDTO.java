package com.cricket.api.cricketapi.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PlayerDTO {
    private Integer id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Matches is required")
    @Min(value = 0, message = "Matches cannot be negative")
    private Integer matches;

    @NotNull(message = "Runs is required")
    @Min(value = 0, message = "Runs cannot be negative")
    private Integer runs;

    @NotNull(message = "Average is required")
    @DecimalMin(value = "0.0", message = "Average cannot be negative")
    private Double average;

    private Integer teamId;

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public PlayerDTO() {
    }

    public PlayerDTO(Integer id, String name, Integer matches, Integer runs, Double average, Integer teamId) {
        this.id = id;
        this.name = name;
        this.matches = matches;
        this.runs = runs;
        this.average = average;
        this.teamId = teamId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMatches() {
        return matches;
    }

    public void setMatches(Integer matches) {
        this.matches = matches;
    }

    public Integer getRuns() {
        return runs;
    }

    public void setRuns(Integer runs) {
        this.runs = runs;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }
}
