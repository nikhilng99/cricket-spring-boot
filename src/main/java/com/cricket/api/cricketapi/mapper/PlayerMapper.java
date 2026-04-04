package com.cricket.api.cricketapi.mapper;

import com.cricket.api.cricketapi.dto.PlayerDTO;
import com.cricket.api.cricketapi.entity.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
    PlayerDTO toDTO(Player player);
    Player toEntity(PlayerDTO dto);
    List<PlayerDTO> toDTOList(List<Player> players);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(PlayerDTO playerDTO, @MappingTarget Player player);
}