package com.cricket.api.cricketapi.controller;

import com.cricket.api.cricketapi.dto.PlayerDTO;
import com.cricket.api.cricketapi.service.PlayerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.json.JsonMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlayerController.class)
@DisplayName("PlayerController Tests")
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PlayerService playerService;

    // ObjectMapper in Springboot 3, JsonMapper in Springboot 4
    @Autowired
    private JsonMapper objectMapper;

    private PlayerDTO playerDTO;

    @BeforeEach
    void setUp() {
        playerDTO = new PlayerDTO(1, "Virat Kohli", 500, 12000, 65.67, 1);
    }

    @Test
    @DisplayName("GET /players should return 200")
    void getPlayers_returns200() throws Exception {
        when(playerService.getAllPlayers(any(Pageable.class)))
                .thenReturn(Page.empty(PageRequest.of(0, 5)));

        mockMvc.perform(get("/api/v1/players"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /players/{id} should return 200 with player")
    void getPlayerById_withValidId_returns200() throws Exception {
        when(playerService.getPlayerById(1)).thenReturn(playerDTO);

        mockMvc.perform(get("/api/v1/players/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Virat Kohli"))
                .andExpect(jsonPath("$.runs").value(12000));
    }

    @Test
    @DisplayName("POST /players should return 201")
    void addPlayer_withValidBody_returns201() throws Exception {
        when(playerService.insertPlayer(any())).thenReturn(playerDTO);

        mockMvc.perform(post("/api/v1/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Virat Kohli"));
    }

    @Test
    @DisplayName("POST /players with invalid body should return 400")
    void addPlayer_withInvalidBody_returns400() throws Exception {
        PlayerDTO invalidDTO = new PlayerDTO(null, "", -1, -1, -1.0, null);

        mockMvc.perform(post("/api/v1/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DELETE /players/{id} should return 204")
    void deletePlayer_withValidId_returns204() throws Exception {
        mockMvc.perform(delete("/api/v1/players/1"))
                .andExpect(status().isNoContent());
    }
}