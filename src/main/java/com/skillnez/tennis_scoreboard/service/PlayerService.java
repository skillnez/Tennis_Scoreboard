package com.skillnez.tennis_scoreboard.service;

import com.skillnez.tennis_scoreboard.dao.PlayerRepository;
import com.skillnez.tennis_scoreboard.entity.Player;
import com.skillnez.tennis_scoreboard.utils.HibernateUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.Proxy;

@ApplicationScoped
public class PlayerService {

    @Inject
    private PlayerRepository playerRepository;

    public Player takeOrSavePlayer (String name) {
       return playerRepository.getByName(name).orElseGet(() -> playerRepository.save(Player.builder().name(name).build()));
    }

}
