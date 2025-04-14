package vinnsla;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * A class that represents the dice used in the game.
 * Has 6 sides.
 */
public class Dice {

    private static final int MAX = 6;
    private final SimpleIntegerProperty number = new SimpleIntegerProperty(MAX);


    /**
     * Rolls the current dice by generating a number from 1 to 6.
     */
    public void throwDice() {
        number.set((int) (Math.random() * MAX) + 1);
    }

    /**
     * Returns the property representing the dice number.
     * @return the SimpleIntegerPropery.
     */
    public SimpleIntegerProperty getProp() {
        return number;
    }

    /**
     * Return the dice number in integer form.
     * @return the number shown on the dice.
     */
    public int getNumber() {
        return number.get();
    }

}
