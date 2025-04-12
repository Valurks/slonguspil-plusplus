package vinnsla;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Nafn: Hjörleifur Örn Sveinsson
 * Gmail: hjorleifursveins@gmail.com
 * Lýsing:
 */
public class BoardBehaviorTest {
    private Game game;
    private BoardBehavior boardBehavior;
    @BeforeEach
    public void setUp() {
        game = new Game(4, 6, 1.0);
        boardBehavior = game.getSnakesAndLadders();
    }

    @Test
    public void testCreateMap(){
        HashMap<Integer, Integer> map = game.getSnakesAndLaddersMap();
        assertFalse(map.isEmpty());
        assertTrue(map.keySet().stream().allMatch(tile -> tile >= 0 && tile <= 24));

    }
    @Test
    public void testLadder(){
        game.setNextPlayer(new Player("Arnar", 24, true));
        boardBehavior.getSnakesAndLadders().put(2,20);
        Player player = game.getNextPlayer();
        player.move(1);
        player.setTile(boardBehavior.newTile(player));
        assertEquals(20,player.getTile());
    }
    @Test
    public void testSnake(){
        game.setNextPlayer(new Player("Petur", 24, true));
        boardBehavior.getSnakesAndLadders().put(18,4);
        Player player = game.getNextPlayer();
        player.move(17);
        player.setTile(boardBehavior.newTile(player));
        assertEquals(4,player.getTile());
    }
    @Test
    public void testEmptyBoard(){
        game = new Game(0, 0, 1.0);
        HashMap<Integer,Integer> emptyMap = game.getSnakesAndLaddersMap();
        assertTrue(emptyMap.isEmpty());
    }
}
