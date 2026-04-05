package com.cricket.api.cricketapi.repository;

import com.cricket.api.cricketapi.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}
