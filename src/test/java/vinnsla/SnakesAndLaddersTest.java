package vinnsla;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Nafn: Hjörleifur Örn Sveinsson
 * Gmail: hjorleifursveins@gmail.com
 * Lýsing:
 */
public class SnakesAndLaddersTest {

    @Test
    public void testCreateMap(){
        SnakesAndLadders game = new SnakesAndLadders(1.0, 24);
        game.createMap();
        HashMap<Integer, Integer> map = game.getSnakesAndLadders();
        assertFalse(map.isEmpty());
        assertTrue(map.keySet().stream().allMatch(tile -> tile >= 0 && tile <= 24));
    }
    @Test
    public void testLadder(){
        Game game = new Game(4,6,1.0);
        game.setNextPlayer(new Player("Arnar", 24, true));
        SnakesAndLadders snakesAndLadders = game.getSnakesAndLadders();
        snakesAndLadders.getSnakesAndLadders().put(2,20);
        Player player = game.getNextPlayer();
        player.move(1);
        player.setTile(snakesAndLadders.newTile(player));
        assertEquals(20,player.getTile());
    }
    @Test
    public void testSnake(){
        Game game = new Game(4,6,1.0);
        game.setNextPlayer(new Player("Petur", 24, true));
        SnakesAndLadders snakesAndLadders = game.getSnakesAndLadders();
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
