package com.cricket.api.cricketapi.repository;

import com.cricket.api.cricketapi.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Integer> {

    // similar to SELECT t.id, t.name, t.country,
    // p.id, p.name, p.matches, p.runs, p.average, p.team_id
    // FROM team t
    //LEFT JOIN player p ON t.id = p.team_id
    @Query("SELECT t FROM Team t LEFT JOIN FETCH t.players")
    List<Team> findAllWithPlayers();

    @Query("SELECT t FROM Team t LEFT JOIN FETCH t.players where t.id = :id")
    Optional<Team> findByIdWithPlayers(@Param("id") Integer id);

}
