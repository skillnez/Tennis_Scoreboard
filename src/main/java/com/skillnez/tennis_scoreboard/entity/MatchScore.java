package com.skillnez.tennis_scoreboard.entity;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
@Getter
@Setter
public class MatchScore {

    private PlayerScore playerOneScore;

    private PlayerScore playerTwoScore;
}
