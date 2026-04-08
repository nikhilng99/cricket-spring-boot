package com.cricket.api.cricketapi.repository;

import com.cricket.api.cricketapi.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

    @Query("SELECT p FROM Player p WHERE p.team.id = :teamId")
    List<Player> findByTeamId(@Param("teamId") Integer teamId);

    @Query("SELECT p FROM Player p WHERE p.team.id = :teamId AND p.runs >= :minRuns")
    List<Player> findByTeamIdAndMinRuns(
            @Param("teamId") Integer teamId,
            @Param("minRuns") Integer minRuns);
}