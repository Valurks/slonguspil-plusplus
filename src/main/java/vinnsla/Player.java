package vinnsla;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Class that represents a player in the game.
 */
public class Player {

    private final SimpleIntegerProperty tile;
    private final SimpleStringProperty name;
    private final SimpleStringProperty message;
    private final int max;
    private final boolean isBot;

    /**
     * Constructs a new player.
     * @param name The name of the player.
     * @param max The maximum number of tiles.
     * @param isBot Whether the player is a bot.
     */
    Player(String name, int max, Boolean isBot) {
        this.name = new SimpleStringProperty(name);
        this.max = max;
        this.isBot = isBot;
        tile = new SimpleIntegerProperty(1);
        message = new SimpleStringProperty(name);
    }

    /**
     * Sets the current tile based on the dice throw.
     * @param diceThrow The number that the dice rolled.
     * @return True if player has finished the game. False if not.
     */
    public boolean move(int diceThrow) {
        int reitur = this.tile.get() + diceThrow;
        if (reitur >= max) {
            this.tile.set(max);
            return true;
        } else {
            this.tile.set(reitur);
            return false;
        }
    }

    /**
     * Returns the integer of current tile.
     * @return The value of the property tile.
     */
    public int getTile() {
        return tile.get();
    }

    /**
     * Returns the String of the name of current player.
     * @return The value of the property name.
     */
    public String getName() {
        return name.get();
    }

    /**
     * Returns the property of the message of current player.
     * @return SimpleStringProperty of the message for current player.
     */
    public SimpleStringProperty getMessage() {
        return message;
    }

    /**
     * Returns the property of current tile.
     * @return SimpleIntegerProperty of the tile of current player.
     */
    public SimpleIntegerProperty getTileProp() {
        return tile;
    }

    /**
     * Sets the current tile.
     * @param tile The current tile.
     */
    public void setTile(int tile) {
        this.tile.set(tile);
    }

    /**
     * Sets the current message.
     * @param message The new message.
     */
    public void setMessage(String message) {
        this.message.set(message);
    }

    /**
     * Returns true if player is a bot.
     * @return Boolean value of whether player is a bot.
     */
    public boolean isBot() {
        return isBot;
    }
}
