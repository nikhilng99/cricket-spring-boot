package com.cricket.api.cricketapi.controller;

import com.cricket.api.cricketapi.dto.PlayerDTO;
import com.cricket.api.cricketapi.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/players")
public class PlayerController {

    private final PlayerService playerService;
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    // Status 200
    @GetMapping("/{id}")
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable Integer id){
        PlayerDTO playerDTO = playerService.getPlayerById(id);
        return ResponseEntity.ok(playerDTO);
    }

    // Status 200
    @GetMapping
    public ResponseEntity<Page<PlayerDTO>> getPlayers(@PageableDefault(size = 5, sort = "name") Pageable pageable){
        Page<PlayerDTO> playerList = playerService.getAllPlayers(pageable);
        return ResponseEntity.ok(playerList);
    }

    // Status 201 - Created
    @PostMapping
    public ResponseEntity<PlayerDTO> addPlayer(@RequestBody @Valid PlayerDTO playerDTO){
        PlayerDTO insertedPlayer = playerService.insertPlayer(playerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(insertedPlayer);
    }

    // Status 204 - No Content
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Integer id){
        playerService.removePlayer(id);
        return ResponseEntity.noContent().build();
    }

    // Status 200
    @PutMapping("/{id}")
    public ResponseEntity<PlayerDTO> updatePlayer(@PathVariable Integer id, @RequestBody @Valid PlayerDTO playerDTO){
        PlayerDTO updated = playerService.updatePlayerById(id, playerDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<PlayerDTO>> getPlayersByTeam(@PathVariable Integer teamId) {
        return ResponseEntity.ok(playerService.getPlayersByTeam(teamId));
    }

    @GetMapping("/team/{teamId}/min-runs/{minRuns}")
    public ResponseEntity<List<PlayerDTO>> getPlayersByTeamWithMinRuns(@PathVariable Integer teamId, @PathVariable Integer minRuns) {
        return ResponseEntity.ok(playerService.getPlayersByTeamWithMinRuns(teamId, minRuns));
    }
}
