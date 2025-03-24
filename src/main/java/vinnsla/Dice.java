package vinnsla;

import javafx.beans.property.SimpleIntegerProperty;

public class Dice {

    private static final int MAX = 6;
    private final SimpleIntegerProperty number = new SimpleIntegerProperty(MAX);


    /**
     * Kastar tening þannig að fundinn sé number af handahófi á bilinu 1 til MAX+1
     */
    public void throwDice() {
        number.set((int) (Math.random() * 6) + 1);
    }

    public SimpleIntegerProperty getProp() {
        return number;
    }

    public int getNumber() {
        return number.get();
    }

    public static void main(String[] args) {
        Dice dice = new Dice();
        for (int i = 0; i < 12; i++) {
            dice.throwDice();
            System.out.print(dice.getNumber() + "\n");
        }
    }
}
