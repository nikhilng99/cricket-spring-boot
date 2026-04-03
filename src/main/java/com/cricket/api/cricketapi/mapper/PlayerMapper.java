package com.cricket.api.cricketapi.mapper;

import com.cricket.api.cricketapi.dto.PlayerDTO;
import com.cricket.api.cricketapi.entity.Player;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
    PlayerDTO toDTO(Player player);
    Player toEntity(PlayerDTO dto);
    List<PlayerDTO> toDTOList(List<Player> players);
}