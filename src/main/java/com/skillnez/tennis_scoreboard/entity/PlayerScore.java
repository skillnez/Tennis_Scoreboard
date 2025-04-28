package com.skillnez.tennis_scoreboard.entity;

import lombok.*;

@Data
@Builder
@Getter
@Setter
public class PlayerScore {

    private Player player;

    private int sets;

    private int games;

    private int points;
}
