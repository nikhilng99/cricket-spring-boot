package com.cricket.api.cricketapi.service;

import com.cricket.api.cricketapi.entity.Player;
import com.cricket.api.cricketapi.exception.ResourceNotFoundException;
import com.cricket.api.cricketapi.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player getPlayerById(Integer id){
        return playerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Player not found with id: " + id));
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player insertPlayer(Player player) {
        return playerRepository.save(player);
    }

    public Player updatePlayerById(Integer id, Player player) {
        Player oldPlayer= playerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Player not found with id: " + id));
        oldPlayer.setAverage(player.getAverage());
        oldPlayer.setMatches(player.getMatches());
        oldPlayer.setName(player.getName());
        oldPlayer.setRuns(player.getRuns());
        return playerRepository.save(oldPlayer);
    }

    public void removePlayer(Integer id) {
        Player player = playerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Player not found with id: " + id));
        playerRepository.delete(player);
    }
}
