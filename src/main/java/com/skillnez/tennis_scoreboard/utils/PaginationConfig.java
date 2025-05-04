package com.skillnez.tennis_scoreboard.utils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Properties;

@ApplicationScoped
public class PaginationConfig {

    @Inject
    @AppProps
    private Properties properties;

    public int getPageSize() {
        return Integer.parseInt(properties.getProperty("pagination.page-size", "5"));
    }

}
