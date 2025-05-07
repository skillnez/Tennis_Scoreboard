import com.skillnez.tennis_scoreboard.entity.Match;
import com.skillnez.tennis_scoreboard.entity.MatchScore;
import com.skillnez.tennis_scoreboard.entity.Player;
import com.skillnez.tennis_scoreboard.entity.PlayerScore;
import com.skillnez.tennis_scoreboard.service.MatchScoreCalculationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerScoreServiceTest {

    private MatchScoreCalculationService matchScoreCalculationService;
    Player playerOne;
    Player playerTwo;

    @BeforeEach
    public void setUp() {
        matchScoreCalculationService = new MatchScoreCalculationService();
        playerOne = new Player();
        playerTwo = new Player();
        playerOne.setId(1);
        playerOne.setName("TestPlayerOne");
        playerTwo.setId(2);
        playerTwo.setName("TestPlayerTwo");
    }

    @Test
    void testFirstPointForPlayerOne() {

        PlayerScore playerOneScore = PlayerScore.builder().player(playerOne).points(0).build();
        PlayerScore playerTwoScore = PlayerScore.builder().player(playerTwo).points(0).build();

        MatchScore matchScore = new MatchScore(playerOneScore, playerTwoScore);

        Match match = Match.builder().matchScore(matchScore).build();

        matchScoreCalculationService.calculateScore(1, match);

        assertEquals(1, match.getMatchScore().getPlayerOneScore().getPoints());
        assertEquals(0, match.getMatchScore().getPlayerTwoScore().getPoints());
    }

    @Test
    void testWinGameForPlayerOne() {
        PlayerScore playerOneScore = PlayerScore.builder().player(playerOne).points(3).build();
        PlayerScore playerTwoScore = PlayerScore.builder().player(playerTwo).points(0).build();

        MatchScore matchScore = new MatchScore(playerOneScore, playerTwoScore);

        Match match = Match.builder().matchScore(matchScore).build();

        matchScoreCalculationService.calculateScore(1, match);

        assertEquals(1, match.getMatchScore().getPlayerOneScore().getGames());
        assertEquals(0, match.getMatchScore().getPlayerTwoScore().getGames());
    }

    @Test
    void checkDeuce() {
        PlayerScore playerOneScore = PlayerScore.builder().player(playerOne).points(3).build();
        PlayerScore playerTwoScore = PlayerScore.builder().player(playerTwo).points(3).build();

        MatchScore matchScore = new MatchScore(playerOneScore, playerTwoScore);

        Match match = Match.builder().matchScore(matchScore).build();

        matchScoreCalculationService.calculateScore(1, match);

        assertEquals(4, match.getMatchScore().getPlayerOneScore().getPoints());
        assertEquals(0, match.getMatchScore().getPlayerOneScore().getGames());
        assertEquals(3, match.getMatchScore().getPlayerTwoScore().getPoints());
    }

    @Test
    void checkWinGameAfterDeuce() {
        PlayerScore playerOneScore = PlayerScore.builder().player(playerOne).points(4).build();
        PlayerScore playerTwoScore = PlayerScore.builder().player(playerTwo).points(3).build();

        MatchScore matchScore = new MatchScore(playerOneScore, playerTwoScore);

        Match match = Match.builder().matchScore(matchScore).build();

        matchScoreCalculationService.calculateScore(1, match);

        assertEquals(0, match.getMatchScore().getPlayerOneScore().getPoints());
        assertEquals(1, match.getMatchScore().getPlayerOneScore().getGames());
        assertEquals(0, match.getMatchScore().getPlayerTwoScore().getGames());
        assertEquals(0, match.getMatchScore().getPlayerTwoScore().getPoints());
    }

    @Test
    void checkTieBreak() {
        PlayerScore playerOneScore = PlayerScore.builder().player(playerOne).games(5).points(3).build();
        PlayerScore playerTwoScore = PlayerScore.builder().player(playerTwo).games(6).build();

        MatchScore matchScore = new MatchScore(playerOneScore, playerTwoScore);

        Match match = Match.builder().matchScore(matchScore).build();

        matchScoreCalculationService.setTieBreak(false);

        matchScoreCalculationService.calculateScore(1, match);

        assertEquals(0, match.getMatchScore().getPlayerOneScore().getPoints());
        assertEquals(0, match.getMatchScore().getPlayerTwoScore().getPoints());
        assertEquals(6, match.getMatchScore().getPlayerOneScore().getGames());
        assertEquals(6, match.getMatchScore().getPlayerTwoScore().getGames());
    }

    @Test
    void winTieBreak() {
        PlayerScore playerOneScore = PlayerScore.builder().player(playerOne).games(6).points(6).build();
        PlayerScore playerTwoScore = PlayerScore.builder().player(playerTwo).games(6).build();

        MatchScore matchScore = new MatchScore(playerOneScore, playerTwoScore);

        Match match = Match.builder().matchScore(matchScore).build();

        matchScoreCalculationService.setTieBreak(true);
        matchScoreCalculationService.calculateScore(1, match);

        assertEquals(0, match.getMatchScore().getPlayerOneScore().getPoints());
        assertEquals(0, match.getMatchScore().getPlayerTwoScore().getPoints());
        assertEquals(0, match.getMatchScore().getPlayerOneScore().getGames());
        assertEquals(0, match.getMatchScore().getPlayerTwoScore().getGames());
        assertEquals(1, match.getMatchScore().getPlayerOneScore().getSets());
        assertEquals(0, match.getMatchScore().getPlayerTwoScore().getSets());
        assertFalse(matchScoreCalculationService.isTieBreak());
    }

    @Test
    void checkExtendedTieBreak() {
        PlayerScore playerOneScore = PlayerScore.builder().player(playerOne).games(6).points(6).build();
        PlayerScore playerTwoScore = PlayerScore.builder().player(playerTwo).games(6).points(6).build();

        MatchScore matchScore = new MatchScore(playerOneScore, playerTwoScore);

        Match match = Match.builder().matchScore(matchScore).build();

        matchScoreCalculationService.setTieBreak(true);
        matchScoreCalculationService.calculateScore(1, match);

        assertEquals(7, match.getMatchScore().getPlayerOneScore().getPoints());
        assertEquals(6, match.getMatchScore().getPlayerTwoScore().getPoints());
        assertEquals(6, match.getMatchScore().getPlayerOneScore().getGames());
        assertEquals(6, match.getMatchScore().getPlayerTwoScore().getGames());
        assertEquals(0, match.getMatchScore().getPlayerOneScore().getSets());
        assertEquals(0, match.getMatchScore().getPlayerTwoScore().getSets());
        assertTrue(matchScoreCalculationService.isTieBreak());
    }

    @Test
    void winExtendedTieBreak() {
        PlayerScore playerOneScore = PlayerScore.builder().player(playerOne).games(6).points(7).build();
        PlayerScore playerTwoScore = PlayerScore.builder().player(playerTwo).games(6).points(6).build();

        MatchScore matchScore = new MatchScore(playerOneScore, playerTwoScore);

        Match match = Match.builder().matchScore(matchScore).build();

        matchScoreCalculationService.setTieBreak(true);
        matchScoreCalculationService.calculateScore(1, match);

        assertEquals(0, match.getMatchScore().getPlayerOneScore().getPoints());
        assertEquals(0, match.getMatchScore().getPlayerTwoScore().getPoints());
        assertEquals(0, match.getMatchScore().getPlayerOneScore().getGames());
        assertEquals(0, match.getMatchScore().getPlayerTwoScore().getGames());
        assertEquals(1, match.getMatchScore().getPlayerOneScore().getSets());
        assertEquals(0, match.getMatchScore().getPlayerTwoScore().getSets());
        assertFalse(matchScoreCalculationService.isTieBreak());
    }

    @Test
    void winMatch() {
        PlayerScore playerOneScore = PlayerScore.builder().player(playerOne).games(6).points(3).sets(1).build();
        PlayerScore playerTwoScore = PlayerScore.builder().player(playerTwo).games(0).points(0).build();

        MatchScore matchScore = new MatchScore(playerOneScore, playerTwoScore);

        Match match = Match.builder().matchScore(matchScore).build();

        matchScoreCalculationService.calculateScore(1, match);

        assertEquals(0, match.getMatchScore().getPlayerOneScore().getPoints());
        assertEquals(0, match.getMatchScore().getPlayerTwoScore().getPoints());
        assertEquals(0, match.getMatchScore().getPlayerOneScore().getGames());
        assertEquals(0, match.getMatchScore().getPlayerTwoScore().getGames());
        assertEquals(2, match.getMatchScore().getPlayerOneScore().getSets());
        assertEquals(0, match.getMatchScore().getPlayerTwoScore().getSets());
        assertTrue(matchScoreCalculationService.isMatchEnded());
    }
}
