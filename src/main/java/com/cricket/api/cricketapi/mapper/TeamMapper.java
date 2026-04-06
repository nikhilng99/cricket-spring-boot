package com.cricket.api.cricketapi.mapper;

import com.cricket.api.cricketapi.dto.TeamDTO;
import com.cricket.api.cricketapi.entity.Team;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {PlayerMapper.class})
public interface TeamMapper {

    TeamDTO toDTO(Team team);

    @Mapping(target = "players", ignore = true)
    Team toEntity(TeamDTO teamDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "players", ignore = true)
    void updateEntityFromDTO(TeamDTO teamDTO, @MappingTarget Team team);
}
