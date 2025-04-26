package com.skillnez.tennis_scoreboard.mapper;

import com.skillnez.tennis_scoreboard.dto.MatchRequestDto;
import com.skillnez.tennis_scoreboard.dto.MatchResponseDto;
import com.skillnez.tennis_scoreboard.entity.Match;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface MatchMapper {

    MatchResponseDto toMatchResponseDto(Match match);
    Match toMatch(MatchResponseDto matchResponseDto);

    MatchRequestDto toMatchRequestDto(Match match);
    Match toMatch(MatchRequestDto matchRequestDto);

}
