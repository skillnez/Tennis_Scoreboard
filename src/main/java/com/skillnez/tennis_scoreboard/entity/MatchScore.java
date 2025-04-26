package com.skillnez.tennis_scoreboard.entity;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
@Getter
@Setter
public class MatchScore {


    /// Вопрос как это будет работать с хибернейтом
    public final static int INIT_SET_VALUE = 0;
    public final static int INIT_GAME_VALUE = 0;
    public final static int INIT_POINTS_VALUE = 0;

    private Match match;

    private int sets;

    private int games;

    private int points;
}
