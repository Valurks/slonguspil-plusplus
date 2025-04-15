package vinnsla;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * A class that tests behavior of Player.
 */
public class PlayerTest {
    private Player player;

    /**
     * Creates a single player.
     */
    @Before
    public void setUp(){
        player = new Player("Arnar", 24, true);
    }

    /**
     * Tests moving the player.
     */
    @Test
    public void testMove(){
        assertEquals(1,player.getTile());
        player.move(4);
        assertEquals(5,player.getTile());
    }

    /**
     * Tests moving the player beyond max.
     */
    @Test
    public void testMaxMove(){
        player.setTile(23);
        player.move(6);
        assertEquals(24, player.getTile());
    }

    /**
     * Tests getPlayer method.
     */
    @Test
    public void testGetPlayer(){
        assertSame("Arnar",player.getName());
    }

    /**
     * Tests getTile method.
     */
    @Test
    public void testGetTile(){
        player.move(5);
        assertEquals(6,player.getTile());
    }

    /**
     * Tests getMessage method.
     */
    @Test
    public void testGetMessage(){
        player.setMessage("12345");
        assertEquals("12345", player.getMessage().get());
    }

    /**
     * Tests isBot method.
     */
    @Test
    public void testIsBot(){
        assertTrue(player.isBot());
    }
}
