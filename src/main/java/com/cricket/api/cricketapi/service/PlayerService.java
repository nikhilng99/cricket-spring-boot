package com.cricket.api.cricketapi.service;

import com.cricket.api.cricketapi.dto.PlayerDTO;
import com.cricket.api.cricketapi.entity.Player;
import com.cricket.api.cricketapi.exception.ResourceNotFoundException;
import com.cricket.api.cricketapi.mapper.PlayerMapper;
import com.cricket.api.cricketapi.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    public PlayerService(PlayerRepository playerRepository, PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    public PlayerDTO getPlayerById(Integer id){
        Player player = playerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Player not found with id: " + id));
        return playerMapper.toDTO(player);
    }

    public List<PlayerDTO> getAllPlayers() {
        return playerMapper.toDTOList(playerRepository.findAll());
    }

    public PlayerDTO insertPlayer(PlayerDTO playerDTO) {
        Player player = playerMapper.toEntity(playerDTO);
        Player savedPlayer = playerRepository.save(player);
        return playerMapper.toDTO(savedPlayer);
    }

    public PlayerDTO updatePlayerById(Integer id, PlayerDTO playerDTO) {
        Player oldPlayer= playerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Player not found with id: " + id));
        oldPlayer.setAverage(playerDTO.getAverage());
        oldPlayer.setMatches(playerDTO.getMatches());
        oldPlayer.setName(playerDTO.getName());
        oldPlayer.setRuns(playerDTO.getRuns());

        Player savedPlayer = playerRepository.save(oldPlayer);
        return playerMapper.toDTO(savedPlayer);
    }

    public void removePlayer(Integer id) {
        Player player = playerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Player not found with id: " + id));
        playerRepository.delete(player);
    }

//    private PlayerDTO toDTO(Player player){
//        return new PlayerDTO(player.getId(),
//                player.getName(),
//                player.getMatches(),
//                player.getRuns(),
//                player.getAverage());
//    }

}
