package com.cricket.api.cricketapi.service;

import com.cricket.api.cricketapi.dto.PlayerDTO;
import com.cricket.api.cricketapi.entity.Player;
import com.cricket.api.cricketapi.entity.Team;
import com.cricket.api.cricketapi.exception.ResourceNotFoundException;
import com.cricket.api.cricketapi.mapper.PlayerMapper;
import com.cricket.api.cricketapi.repository.PlayerRepository;
import com.cricket.api.cricketapi.repository.TeamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;
    private final TeamRepository teamRepository;

    public PlayerService(PlayerRepository playerRepository, PlayerMapper playerMapper, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
        this.teamRepository = teamRepository;
    }

    public PlayerDTO getPlayerById(Integer id){
        Player player = playerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Player not found with id: " + id));
        return playerMapper.toDTO(player);
    }

    public Page<PlayerDTO> getAllPlayers(Pageable pageable) {
        return playerRepository.findAll(pageable).map(playerMapper::toDTO);
    }

    public List<PlayerDTO> getAllPlayers() {
        List<Player> playerList = playerRepository.findAll();
        return playerMapper.toDTOList(playerList);
    }

    public PlayerDTO insertPlayer(PlayerDTO playerDTO) {
        Player player = playerMapper.toEntity(playerDTO);
        if (playerDTO.getTeamId() != null) {
            Team team = teamRepository.findById(playerDTO.getTeamId()).orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + playerDTO.getTeamId()));
            player.setTeam(team);
        }
        return playerMapper.toDTO(playerRepository.save(player));
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

    public List<PlayerDTO> getPlayersByTeam(Integer teamId) {
        return playerMapper.toDTOList(playerRepository.findByTeamId(teamId));
    }

    public List<PlayerDTO> getPlayersByTeamWithMinRuns(Integer teamId, Integer minRuns) {
        return playerMapper.toDTOList(
                playerRepository.findByTeamIdAndMinRuns(teamId, minRuns)
        );
    }
}
