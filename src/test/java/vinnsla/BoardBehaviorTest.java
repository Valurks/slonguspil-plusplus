package vinnsla;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Test class for the BoardBehavior class.
 * Tests basic functionality of snakes and ladder behavior.
 */
public class BoardBehaviorTest {
    private Game game;
    private BoardBehavior boardBehavior;

    /**
     * Sets a standard game before each tets.
     */
    @Before
    public void setUp() {
        game = new Game(4, 6, 1.0);
        boardBehavior = game.getBoardBehavior();
    }

    /**
     * Tests that creating a map contains valid keys.
     */
    @Test
    public void testCreateMap(){
        HashMap<Integer, Integer> map = game.getConnectionMap();
        assertFalse(map.isEmpty());
        assertTrue(map.keySet().stream().allMatch(tile -> tile >= 0 && tile <= 24));

    }

    /**
     * Tests that players correctly climb ladders.
     */
    @Test
    public void testLadder(){
        game.setNextPlayer(new Player("Arnar", 24, true));
        boardBehavior.getConnectionMap().put(2,20);
        Player player = game.getNextPlayer();
        player.move(1);
        player.setTile(boardBehavior.newTile(player));
        assertEquals(20,player.getTile());
    }

    /**
     * Tests that players correctly slide down snakes.
     */
    @Test
    public void testSnake(){
        game.setNextPlayer(new Player("Petur", 24, true));
        boardBehavior.getConnectionMap().put(18,4);
        Player player = game.getNextPlayer();
        player.move(17);
        player.setTile(boardBehavior.newTile(player));
        assertEquals(4,player.getTile());
    }

    /**
     * Tests behavior of an empty board.
     */
    @Test
    public void testEmptyBoard(){
        game = new Game(0, 0, 1.0);
        HashMap<Integer,Integer> emptyMap = game.getConnectionMap();
        assertTrue(emptyMap.isEmpty());
    }
}
