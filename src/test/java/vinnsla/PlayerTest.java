package vinnsla;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Nafn: Hjörleifur Örn Sveinsson
 * Gmail: hjorleifursveins@gmail.com
 * Lýsing:
 */
public class PlayerTest {
    private Player player;
    @Test
    public void testCreatingPlayer(){
        player = new Player("Arnar", 24, true);
        assertEquals("Arnar", player.getName());
        assertTrue(player.isBot());
    }
    @Test
    public void testMove(){
        player = new Player("Petur", 24, false);
        assertEquals(1,player.getTile());
        player.move(4);
        assertEquals(5,player.getTile());
    }
    @Test
    public void testMaxMove(){
        player = new Player("Gaukur", 24, false);
        player.setTile(23);
        player.move(6);
        assertEquals(24, player.getTile());
    }
}
