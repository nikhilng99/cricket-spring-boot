package com.cricket.api.cricketapi.service;

import com.cricket.api.cricketapi.entity.Player;
import com.cricket.api.cricketapi.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public Player getPlayerById(Integer id){
        return playerRepository.findById(id).orElseThrow(()-> new IllegalStateException("Player not found with id: "+ id));
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public void insertPlayer(Player player) {
        playerRepository.save(player);
    }

    public void updatePlayerById(Integer id, Player player) {
        Player oldPlayer= playerRepository.findById(id).orElseThrow(()->new IllegalStateException("Not found"));
        oldPlayer.setAverage(player.getAverage());
        oldPlayer.setMatches(player.getMatches());
        oldPlayer.setName(player.getName());
        oldPlayer.setRuns(player.getRuns());
        playerRepository.save(oldPlayer);

    }

    public void removePlayer(Integer id) {
        playerRepository.deleteById(id);
    }
}
