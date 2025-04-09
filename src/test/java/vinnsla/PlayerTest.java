package vinnsla;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Nafn: Hjörleifur Örn Sveinsson
 * Gmail: hjorleifursveins@gmail.com
 * Lýsing:
 */
class PlayerTest {
    private Player player;
    @BeforeEach
    public void setUp(){
        player = new Player("Arnar", 24, true);
    }
    @Test
    public void testMove(){
        assertEquals(1,player.getTile());
        player.move(4);
        assertEquals(5,player.getTile());
    }
    @Test
    public void testMaxMove(){
        player.setTile(23);
        player.move(6);
        assertEquals(24, player.getTile());
    }
    @Test
    public void testGetPlayer(){
        assertSame("Arnar",player.getName());
    }
    @Test
    public void testGetTile(){
        player.move(5);
        assertEquals(6,player.getTile());
    }
    @Test
    public void testGetMessage(){
        player.setMessage("12345");
        assertEquals("12345", player.getMessage().get());
    }
    @Test
    public void testIsBot(){
        assertTrue(player.isBot());
    }
}
