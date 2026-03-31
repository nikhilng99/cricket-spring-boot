package com.cricket.api.cricketapi.controller;

import com.cricket.api.cricketapi.entity.Player;
import com.cricket.api.cricketapi.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("{id}")
    public Player getPlayerById(@PathVariable Integer id){
        return playerService.getPlayerById(id);
    }

    @GetMapping
    public List<Player> getPlayers(){
        return playerService.getAllPlayers();
    }

    @PostMapping
    public void addPlayer(@RequestBody Player player){
        playerService.insertPlayer(player);
    }

    @DeleteMapping("{id}")
    public void deletePlayer(@PathVariable Integer id){
        playerService.removePlayer(id);
    }

    @PutMapping("{id}")
    public void updatePlayer(@PathVariable Integer id, @RequestBody Player player){
        playerService.updatePlayerById(id, player);
    }
}
