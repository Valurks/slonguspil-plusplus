package vinnsla;

import javafx.beans.property.SimpleIntegerProperty;

public class Dice {

    private static final int MAX = 6;
    private final SimpleIntegerProperty number = new SimpleIntegerProperty(MAX);


    /**
     * Kastar tening þannig að fundinn sé number af handahófi á bilinu 1 til MAX+1
     */
    public void throwDice() {
        number.set((int) (Math.random() * MAX) + 1);
    }

    public SimpleIntegerProperty getProp() {
        return number;
    }

    public int getNumber() {
        return number.get();
    }

}
