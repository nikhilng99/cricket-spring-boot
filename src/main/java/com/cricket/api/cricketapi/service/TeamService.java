package com.cricket.api.cricketapi.service;

import com.cricket.api.cricketapi.dto.TeamDTO;
import com.cricket.api.cricketapi.entity.Team;
import com.cricket.api.cricketapi.exception.ResourceNotFoundException;
import com.cricket.api.cricketapi.mapper.TeamMapper;
import com.cricket.api.cricketapi.repository.TeamRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    public TeamService(TeamRepository teamRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    public TeamDTO getTeamById(Integer id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + id));
        return teamMapper.toDTO(team);
    }

    public TeamDTO createTeam(TeamDTO teamDTO) {
        Team team = teamMapper.toEntity(teamDTO);
        Team savedTeam = teamRepository.save(team);
        return teamMapper.toDTO(savedTeam);
    }

    public List<TeamDTO> getAllTeams() {
        return teamRepository.findAll()
                .stream()
                .map(teamMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TeamDTO updateTeam(Integer id, TeamDTO teamDTO) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + id));
        teamMapper.updateEntityFromDTO(teamDTO, team);
        return teamMapper.toDTO(teamRepository.save(team));
    }
}
