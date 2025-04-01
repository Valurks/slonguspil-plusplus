package vinnsla;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Player {

    private final SimpleIntegerProperty tile;
    private SimpleStringProperty name;
    private final SimpleStringProperty message;
    private final int max;
    private boolean isBot;

    public Player(String name, int max, Boolean isBot) {
        this.name = new SimpleStringProperty(name);
        this.max = max;
        this.isBot = isBot;
        tile = new SimpleIntegerProperty(1);
        message = new SimpleStringProperty("");
    }

    public boolean move(int kast) {
        int reitur = this.tile.get() + kast;
        if (reitur >= max) {
            this.tile.set(max);
            return true;
        } else {
            this.tile.set(reitur);
            return false;
        }
    }

    public int getTile() {
        return tile.get();
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty getMessage() {
        return message;
    }

    public SimpleIntegerProperty getTileProp() {
        return tile;
    }

    public void setTile(int reitur) {
        this.tile.set(reitur);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setMessage(String message) {
        this.message.set(message);
    }

    public boolean isBot() {
        return isBot;
    }

    public void setBot(boolean isBot) {
        this.isBot = isBot;
    }
}
