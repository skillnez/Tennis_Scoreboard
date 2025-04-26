package com.skillnez.tennis_scoreboard.utils;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError("Initial SessionFactory failed: " + ex);
        }
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
