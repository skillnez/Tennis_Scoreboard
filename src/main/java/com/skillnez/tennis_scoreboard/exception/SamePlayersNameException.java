package com.skillnez.tennis_scoreboard.exception;

public class SamePlayersNameException extends RuntimeException {
    public SamePlayersNameException(String message) {
        super(message);
    }
}
