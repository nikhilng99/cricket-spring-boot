package com.cricket.api.cricketapi.service;

import com.cricket.api.cricketapi.dto.PlayerDTO;
import com.cricket.api.cricketapi.entity.Player;
import com.cricket.api.cricketapi.exception.ResourceNotFoundException;
import com.cricket.api.cricketapi.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public PlayerDTO getPlayerById(Integer id){
        Player player = playerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Player not found with id: " + id));
        return toDTO(player);
    }

    public List<PlayerDTO> getAllPlayers() {
        return playerRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public PlayerDTO insertPlayer(PlayerDTO playerDTO) {
        Player player = new Player();
        player.setName(playerDTO.getName());
        player.setRuns(playerDTO.getRuns());
        player.setMatches(playerDTO.getMatches());
        player.setAverage(playerDTO.getAverage());

        Player savedPlayer = playerRepository.save(player);
        return toDTO(savedPlayer);
    }

    public PlayerDTO updatePlayerById(Integer id, PlayerDTO playerDTO) {
        Player oldPlayer= playerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Player not found with id: " + id));
        oldPlayer.setAverage(playerDTO.getAverage());
        oldPlayer.setMatches(playerDTO.getMatches());
        oldPlayer.setName(playerDTO.getName());
        oldPlayer.setRuns(playerDTO.getRuns());

        Player savedPlayer = playerRepository.save(oldPlayer);
        return toDTO(savedPlayer);
    }

    public void removePlayer(Integer id) {
        Player player = playerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Player not found with id: " + id));
        playerRepository.delete(player);
    }

    private PlayerDTO toDTO(Player player){
        return new PlayerDTO(player.getId(),
                player.getName(),
                player.getMatches(),
                player.getRuns(),
                player.getAverage());
    }

}
