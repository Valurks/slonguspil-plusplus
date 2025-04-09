package vinnsla;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Nafn: Hjörleifur Örn Sveinsson
 * Gmail: hjorleifursveins@gmail.com
 * Lýsing:
 */
public class DiceTest {
    private Dice dice;
    @BeforeEach
    public void setUp(){
        dice = new Dice();
    }
    @Test
    public void testRoll(){
        for(int i = 0; i < 100; i++){
            dice.throwDice();
            int result = dice.getNumber();
            assertTrue(result >= 1 && result <= 6);
        }

    }
    @Test
    public void testBeforeRolling(){
        int result = dice.getNumber();
        assertEquals(6, result);
    }

}
