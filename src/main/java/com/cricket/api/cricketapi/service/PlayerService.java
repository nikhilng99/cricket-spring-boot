package com.cricket.api.cricketapi.service;

import com.cricket.api.cricketapi.dto.PlayerDTO;
import com.cricket.api.cricketapi.entity.Player;
import com.cricket.api.cricketapi.exception.ResourceNotFoundException;
import com.cricket.api.cricketapi.mapper.PlayerMapper;
import com.cricket.api.cricketapi.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    public PlayerService(PlayerRepository playerRepository, PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    public PlayerDTO getPlayerById(Integer id){
        Player player = playerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Player not found with id: " + id));
        return playerMapper.toDTO(player);
    }

    public List<PlayerDTO> getAllPlayers() {
        return playerMapper.toDTOList(playerRepository.findAll());
    }

    public PlayerDTO insertPlayer(PlayerDTO playerDTO) {
        Player savedPlayer = playerRepository.save(playerMapper.toEntity(playerDTO));
        return playerMapper.toDTO(savedPlayer);
    }

    @Transactional
    public PlayerDTO updatePlayerById(Integer id, PlayerDTO playerDTO) {
        Player player= playerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Player not found with id: " + id));
        playerMapper.updateEntityFromDTO(playerDTO, player);

        Player savedPlayer = playerRepository.save(player);
        return playerMapper.toDTO(savedPlayer);
    }

    @Transactional
    public void removePlayer(Integer id) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Player not found with id: " + id));
        playerRepository.delete(player);
    }
}
