package vinnsla;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * A class that test the behavior of the game class.
 */
public class GameTest {
    private Game game;

    /**
     * Creates a new game and adds one player.
     */
    @Before
    public void setUp() {
        game = new Game(4,6,1.0);
        game.addPlayer("playerA", false, 0);
        game.newGame();
    }

    /**
     * Tests adding players and checking values.
     */
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

    /**
     * Tests round method that updates nextPlayer.
     */
    @Test
    public void testRound() {
        game.addPlayer("playerB",false,1);
        game.newGame();
        assertEquals("playerA", game.getNextPlayer().getName());
        game.round();
        assertEquals("playerB", game.getNextPlayer().getName());
    }

    /**
     * Tests if round correctly returns -1 when nextPlayer is null.
     */
    @Test
    public void testRoundWithNoPlayers() {
        game = new Game(4,6,1.0);
        assertEquals(-1, game.round());
    }

    /**
     * Tests if round correctly returns 0 if the game should move on.
     */
    @Test
    public void testRoundWithOnePlayer() {
        assertEquals(0, game.round());
    }

    /**
     * Tests if round correctly returns 1 if player has reached the end.
     */
    @Test
    public void testRoundToMax() {
        Player player = game.getNextPlayer();
        player.setTile(23);
        player.move(5);
        assertEquals(1,game.round());
    }

    /**
     * Tests getNextPlayer method.
     */
    @Test
    public void testGetNextPlayer() {
        Player[] players = game.getPlayers();
        assertEquals(game.getNextPlayer().getName(),players[0].getName());
    }

}
