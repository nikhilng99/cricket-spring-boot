package com.cricket.api.cricketapi.mapper;

import com.cricket.api.cricketapi.dto.PlayerDTO;
import com.cricket.api.cricketapi.entity.Player;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
    @Mapping(source = "team.id", target = "teamId")
    PlayerDTO toDTO(Player player);

    @Mapping(target = "team", ignore = true)
    Player toEntity(PlayerDTO dto);

    List<PlayerDTO> toDTOList(List<Player> players);

    // If a field in the DTO is null, skip it — don't overwrite the existing value.
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "team", ignore = true)
    void updateEntityFromDTO(PlayerDTO playerDTO, @MappingTarget Player player);
}