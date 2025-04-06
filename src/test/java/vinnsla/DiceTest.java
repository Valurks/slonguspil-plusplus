package vinnsla;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Nafn: Hjörleifur Örn Sveinsson
 * Gmail: hjorleifursveins@gmail.com
 * Lýsing:
 */
public class DiceTest {
    private Dice dice;
    @Test
    public void testRoll(){
        dice = new Dice();
        for(int i = 0; i < 100; i++){
            dice.throwDice();
            int result = dice.getNumber();
            assertTrue(result >= 1 && result <= 6);
        }

    }
    @Test
    public void testBeforeRolling(){
        dice = new Dice();
        int result = dice.getNumber();
        assertEquals(6, result);
    }

}
