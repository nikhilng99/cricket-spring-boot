package com.cricket.api.cricketapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String name;
    private Integer matches;
    private Integer runs;
    private Double average;

    public Player() {
    }

    public Player(Integer id, String name, Integer matches, Integer runs, Double average) {
        this.id = id;
        this.name = name;
        this.matches = matches;
        this.runs = runs;
        this.average = average;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMatches() {
        return matches;
    }

    public void setMatches(Integer matches) {
        this.matches = matches;
    }

    public Integer getRuns() {
        return runs;
    }

    public void setRuns(Integer runs) {
        this.runs = runs;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id) && Objects.equals(name, player.name) && Objects.equals(matches, player.matches) && Objects.equals(runs, player.runs) && Objects.equals(average, player.average);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, matches, runs, average);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", matches=" + matches +
                ", runs=" + runs +
                ", average=" + average +
                '}';
    }
}
