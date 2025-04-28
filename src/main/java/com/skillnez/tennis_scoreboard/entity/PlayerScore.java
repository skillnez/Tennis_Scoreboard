package com.skillnez.tennis_scoreboard.entity;

import lombok.*;

@Data
@Builder
@Getter
@Setter
public class PlayerScore {

    private Player player;

    private int sets;

    public void addSets () {
        sets++;
    }

    public void addGames() {
        games++;
    }

    public void addPoints () {
        points++;
    }

    private int games;

    private int points;
}
