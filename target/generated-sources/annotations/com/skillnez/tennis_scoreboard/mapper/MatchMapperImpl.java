package com.skillnez.tennis_scoreboard.mapper;

import com.skillnez.tennis_scoreboard.dto.MatchRequestDto;
import com.skillnez.tennis_scoreboard.dto.MatchResponseDto;
import com.skillnez.tennis_scoreboard.entity.Match;
import jakarta.enterprise.context.ApplicationScoped;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-25T19:53:49+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@ApplicationScoped
public class MatchMapperImpl implements MatchMapper {

    @Override
    public MatchResponseDto toMatchResponseDto(Match match) {
        if ( match == null ) {
            return null;
        }

        int winnerId = 0;

        winnerId = match.getWinnerId();

        int matchId = 0;
        int player1Id = 0;
        int player2Id = 0;

        MatchResponseDto matchResponseDto = new MatchResponseDto( matchId, player1Id, player2Id, winnerId );

        return matchResponseDto;
    }

    @Override
    public Match toMatch(MatchResponseDto matchResponseDto) {
        if ( matchResponseDto == null ) {
            return null;
        }

        Match.MatchBuilder match = Match.builder();

        match.winnerId( matchResponseDto.winnerId() );

        return match.build();
    }

    @Override
    public MatchRequestDto toMatchRequestDto(Match match) {
        if ( match == null ) {
            return null;
        }

        int player1Id = 0;
        int player2Id = 0;

        MatchRequestDto matchRequestDto = new MatchRequestDto( player1Id, player2Id );

        return matchRequestDto;
    }

    @Override
    public Match toMatch(MatchRequestDto matchRequestDto) {
        if ( matchRequestDto == null ) {
            return null;
        }

        Match.MatchBuilder match = Match.builder();

        return match.build();
    }
}
