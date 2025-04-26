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
    PlayerRepository playerRepository;

    public Player takePlayer (String name) {
        if (playerRepository.getByName(name).isEmpty()){
            return createPlayer(name);
        }
        return playerRepository.getByName(name).get();
    }

    private Player createPlayer (String name) {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        @Cleanup var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(),
                new Class[]{Session.class},
                ((proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1)));
        session.beginTransaction();
        Player player = playerRepository.save(takePlayer(name));
        session.getTransaction().commit();
        return player;
    }

}
