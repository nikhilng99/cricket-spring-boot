package com.cricket.api.cricketapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class TeamDTO {
    private Integer id;

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Country is required")
    @NotBlank(message = "Country cannot be blank")
    private String country;

    private List<PlayerDTO> players;

    public TeamDTO(Integer id, String name, String country, List<PlayerDTO> playerDTOList) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.players = playerDTOList;
    }

    public TeamDTO() {
    }

    public List<PlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDTO> players) {
        this.players = players;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
