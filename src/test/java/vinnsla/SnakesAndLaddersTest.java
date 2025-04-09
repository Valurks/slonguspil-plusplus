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
public class SnakesAndLaddersTest {
    private Game game;
    private SnakesAndLadders snakesAndLadders;
    @BeforeEach
    public void setUp() {
        game = new Game(4, 6, 1.0);
        snakesAndLadders = game.getSnakesAndLadders();
    }

    @Test
    public void testCreateMap(){
        SnakesAndLadders customBoard = new SnakesAndLadders(1.0, 24);
        customBoard.createMap();
        HashMap<Integer, Integer> map = customBoard.getSnakesAndLadders();
        assertFalse(map.isEmpty());
        assertTrue(map.keySet().stream().allMatch(tile -> tile >= 0 && tile <= 24));
    }
    @Test
    public void testLadder(){
        game.setNextPlayer(new Player("Arnar", 24, true));
        snakesAndLadders.getSnakesAndLadders().put(2,20);
        Player player = game.getNextPlayer();
        player.move(1);
        player.setTile(snakesAndLadders.newTile(player));
        assertEquals(20,player.getTile());
    }
    @Test
    public void testSnake(){
        game.setNextPlayer(new Player("Petur", 24, true));
        snakesAndLadders.getSnakesAndLadders().put(18,4);
        Player player = game.getNextPlayer();
        player.move(17);
        player.setTile(snakesAndLadders.newTile(player));
        assertEquals(4,player.getTile());
    }
    @Test
    public void testEmptyBoard(){
        SnakesAndLadders snakesAndLadders = new SnakesAndLadders(1.0,0);
        snakesAndLadders.createMap();
        assertTrue(snakesAndLadders.getSnakesAndLadders().isEmpty());
    }
}
