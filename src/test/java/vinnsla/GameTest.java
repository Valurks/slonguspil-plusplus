package vinnsla;

import org.junit.Test;

import static org.junit.Assert.*;

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

        game.addPlayer("Hjölli", false, 0);
        assertNotNull(players[0]);
        assertEquals("Hjölli",players[0].getName());
        assertFalse(players[0].isBot());

    }

}
