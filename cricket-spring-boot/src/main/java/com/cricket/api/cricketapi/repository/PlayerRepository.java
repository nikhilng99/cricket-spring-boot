package com.cricket.api.cricketapi.repository;

import com.cricket.api.cricketapi.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Integer> {
}
