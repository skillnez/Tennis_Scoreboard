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

    public List<Match> getPagedMatches (int pageNumber, String playerName) {
        int pageSize = paginationConfig.getPageSize();
        if (playerName == null || playerName.isEmpty() || playerName.isBlank()) {
            return  matchRepository.findPagedMatches(pageNumber, pageSize);
        }
        return  matchRepository.findPagedMatchesByPlayerName(pageNumber, pageSize, playerName);
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
        if (startPage < 1) {
            startPage = 1;
            return startPage;
        }
        return startPage;
    }

    public long getEndPage(int pageNumber, String playerName) {
        long totalPages = getTotalPages(playerName);
        int endPage = pageNumber + 2;
        if (endPage > totalPages){
            return totalPages;
        }
        return endPage;
    }

}
