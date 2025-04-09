package vinnsla;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Nafn: Hjörleifur Örn Sveinsson
 * Gmail: hjorleifursveins@gmail.com
 * Lýsing:
 */
public class GameTest {
    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game(4,6,1.0);
        game.addPlayer("playerA", false, 0);
        game.newGame();
    }

    @Test
    public void testAddPlayers() {
        Player[] players = game.getPlayers();
        game.addPlayer("playerB", true, 1);
        assertNotNull(players[0]);
        assertEquals("playerA", players[0].getName());
        assertEquals("playerB", players[1].getName());
        assertFalse(players[0].isBot());
        assertTrue(players[1].isBot());

    }

    @Test
    public void testRound() {
        game.addPlayer("playerB",false,1);
        game.newGame();
        assertEquals("playerA", game.getNextPlayer().getName());
        game.round();
        assertEquals("playerB", game.getNextPlayer().getName());
    }

    @Test
    public void testRoundWithNoPlayers() {
        game = new Game(4,6,1.0);
        assertEquals(-1, game.round());
    }

    @Test
    public void testRoundWithOnePlayer() {
        assertEquals(0, game.round());
    }

    @Test
    public void testRoundToMax() {
        Player player = game.getNextPlayer();
        player.setTile(23);
        player.move(5);
        assertEquals(1,game.round());
    }

    @Test
    public void testGetNextPlayer() {
        Player[] players = game.getPlayers();
        assertEquals(game.getNextPlayer().getName(),players[0].getName());
    }

}
