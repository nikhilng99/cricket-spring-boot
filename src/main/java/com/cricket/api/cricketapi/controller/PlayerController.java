package com.cricket.api.cricketapi.controller;

import com.cricket.api.cricketapi.entity.Player;
import com.cricket.api.cricketapi.service.PlayerService;
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
    public ResponseEntity<Player> getPlayerById(@PathVariable Integer id){
        Player player = playerService.getPlayerById(id);
        return ResponseEntity.ok(player);
    }

    // Status 200
    @GetMapping
    public ResponseEntity<List<Player>> getPlayers(){
        List<Player> playerList = playerService.getAllPlayers();
        return ResponseEntity.ok(playerList);
    }

    // Status 201 - Created
    @PostMapping
    public ResponseEntity<Player> addPlayer(@RequestBody Player player){
        Player insertedPlayer = playerService.insertPlayer(player);
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
    public ResponseEntity<Player> updatePlayer(@PathVariable Integer id, @RequestBody Player player){
        Player updated = playerService.updatePlayerById(id, player);
        return ResponseEntity.ok(updated);
    }
}
