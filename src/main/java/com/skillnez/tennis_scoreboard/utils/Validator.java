package com.skillnez.tennis_scoreboard.utils;

import com.skillnez.tennis_scoreboard.exception.SamePlayersNameException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Validator {

    public void validate(String player1, String player2) {
        if (player1.equalsIgnoreCase(player2)) {
            throw new SamePlayersNameException("Player 1 and Player 2 must not be the same");
        }
        if (player1.isBlank() || player2.isBlank()) {
            throw new IllegalArgumentException("Players name must not be blank");
        }
        if (!player1.matches("[A-Za-zА-Яа-яёЁ\\s'-]+") || !player2.matches("[A-Za-zА-Яа-яёЁ\\s'-]+")) {
            throw new IllegalArgumentException("Players name must contain only letters");
        }
        if (player1.length() > 16 || player2.length() > 16) {
            throw new IllegalArgumentException("Players name length must be between 1 and 16 characters");
        }
        if (player1 == null || player2 == null) {
            throw new IllegalArgumentException("Player names must not be null");
        }
    }

}
