package vinnsla;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Nafn: Hjörleifur Örn Sveinsson
 * Gmail: hjorleifursveins@gmail.com
 * Lýsing:
 */
public class GameTest {
    private Game game;

    @Test
    public void testAddPlayers(){
        game = new Game(6,4,1.0);
        Player[] players = game.getPlayers();

        game.addPlayer("PlayerA", false, 0);
        game.addPlayer("PlayerB", true, 1);
        assertNotNull(players[0]);
        assertEquals("PlayerA",players[0].getName());
        assertEquals("PlayerB",players[1].getName());
        assertFalse(players[0].isBot());
        assertTrue(players[1].isBot());

    }
    @Test
    public void testRound(){
        game = new Game(6,4,1.0);
        game.addPlayer("playerA", false, 0);
        game.addPlayer("playerB", false, 1);
        game.newGame();
        assertEquals("playerA",game.getNextPlayer().getName());
        game.round();
        assertEquals("playerB",game.getNextPlayer().getName());
    }

    @Test
    public void testRoundWithNoPlayers(){
        game = new Game(6,4,1.0);
        assertEquals(-1, game.round());
    }

}
