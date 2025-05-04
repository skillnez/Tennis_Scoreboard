package com.skillnez.tennis_scoreboard.utils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.hibernate.SessionFactory;

@ApplicationScoped
public class Resources {

    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Produces
    public SessionFactory produceSessionFactory() {
        return sessionFactory;
    }
}
