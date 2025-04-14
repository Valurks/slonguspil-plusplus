package vinnsla;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class that tests behavior of a dice.
 */
public class DiceTest {
    private Dice dice;

    /**
     * Creates a dice.
     */
    @Before
    public void setUp(){
        dice = new Dice();
    }

    /**
     * Tests rolling a dice.
     * Check whether all values are within the right boundaries.
     */
    @Test
    public void testRoll(){
        for(int i = 0; i < 100; i++){
            dice.throwDice();
            int result = dice.getNumber();
            assertTrue(result >= 1 && result <= 6);
        }

    }

    /**
     * Test the correct value before rolling.
     * The initial value of the dice is 6.
     */
    @Test
    public void testBeforeRolling(){
        int result = dice.getNumber();
        assertEquals(6, result);
    }

}
