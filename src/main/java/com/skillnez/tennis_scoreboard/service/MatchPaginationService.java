package com.skillnez.tennis_scoreboard.service;

import com.skillnez.tennis_scoreboard.dao.MatchRepository;
import com.skillnez.tennis_scoreboard.entity.Match;
import com.skillnez.tennis_scoreboard.utils.PaginationConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.Cleanup;

import java.util.List;

@ApplicationScoped
public class MatchPaginationService {

    @Inject
    MatchRepository matchRepository;
    @Inject
    PaginationConfig paginationConfig;

    private final static int FIRST_PAGE = 1;

    public List<Match> getPagedMatches(int pageNumber, String playerName) {
        int pageSize = paginationConfig.getPageSize();
        if (playerName == null || playerName.isEmpty() || playerName.isBlank()) {
            return matchRepository.findPagedMatches(pageNumber, pageSize);
        }
        return matchRepository.findPagedMatchesByPlayerName(pageNumber, pageSize, playerName);
    }

    public int getTotalPages(String playerName) {
        int pageSize = paginationConfig.getPageSize();
        long matchesCount = matchRepository.countAllMatches();
        if (playerName != null && !playerName.isEmpty() && !playerName.isBlank()) {
            matchesCount = matchRepository.countFilteredMatches(playerName);
        }
        return (int) Math.ceil((double) matchesCount / pageSize);
    }

    public long getStartPage(int pageNumber) {
        int startPage = pageNumber - 2;
        return Math.max(startPage, FIRST_PAGE);
    }

    public long getEndPage(int pageNumber, String playerName) {
        long totalPages = getTotalPages(playerName);
        int endPage = pageNumber + 2;
        if (totalPages == 0) {
            return FIRST_PAGE;
        }
        if (endPage > totalPages) {
            return totalPages;
        }
        return endPage;
    }

    public int validatePage(String pageNumber, String playerName) {
        if (pageNumber == null || pageNumber.isEmpty() || pageNumber.isBlank()) {
            return FIRST_PAGE;
        }
        try {
            int page = Integer.parseInt(pageNumber);
            int totalPages = getTotalPages(playerName);
            if (totalPages == 0) {
                return FIRST_PAGE;
            }
            if (page > totalPages) {
                return totalPages;
            }
            return Math.max(page, 1);
        } catch (NumberFormatException e) {
            return FIRST_PAGE;
        }
    }

}
