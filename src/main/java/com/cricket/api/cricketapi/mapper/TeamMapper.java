package com.cricket.api.cricketapi.mapper;

import com.cricket.api.cricketapi.dto.TeamDTO;
import com.cricket.api.cricketapi.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PlayerMapper.class})
public interface TeamMapper {

    TeamDTO toDTO(Team team);

    @Mapping(target = "players", ignore = true)
    Team toEntity(TeamDTO teamDTO);
}
