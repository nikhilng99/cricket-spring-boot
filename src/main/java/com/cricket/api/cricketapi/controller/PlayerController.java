package com.cricket.api.cricketapi.controller;

import com.cricket.api.cricketapi.dto.PlayerDTO;
import com.cricket.api.cricketapi.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Players", description = "Endpoints for managing cricket players")
public class PlayerController {

    private final PlayerService playerService;
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    // Status 200
    @Operation(summary = "Get player by ID")
    @ApiResponse(responseCode = "200", description = "Player found")
    @ApiResponse(responseCode = "404", description = "Player not found")
    @GetMapping("/{id}")
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable Integer id){
        PlayerDTO playerDTO = playerService.getPlayerById(id);
        return ResponseEntity.ok(playerDTO);
    }

    // Status 200
    @Operation(summary = "Get all players", description = "Returns paginated list of all players")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved players")
    @GetMapping
    public ResponseEntity<Page<PlayerDTO>> getPlayers(@PageableDefault(size = 5, sort = "name") Pageable pageable){
        Page<PlayerDTO> playerList = playerService.getAllPlayers(pageable);
        return ResponseEntity.ok(playerList);
    }

    // Status 201 - Created
    @Operation(summary = "Create a new player")
    @ApiResponse(responseCode = "201", description = "Player created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
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
