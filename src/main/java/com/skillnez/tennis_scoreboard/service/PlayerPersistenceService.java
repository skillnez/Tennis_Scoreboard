package com.skillnez.tennis_scoreboard.service;

import com.skillnez.tennis_scoreboard.dao.PlayerRepository;
import com.skillnez.tennis_scoreboard.entity.Player;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PlayerPersistenceService {

    @Inject
    private PlayerRepository playerRepository;

    public Player takeOrSavePlayer (String name) {
       return playerRepository.getByName(name).orElseGet(() -> playerRepository.save(Player.builder().name(name).build()));
    }

}
