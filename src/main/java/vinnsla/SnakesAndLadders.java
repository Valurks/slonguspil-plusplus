package vinnsla;

import java.util.HashMap;

public class SnakesAndLadders {

    private final HashMap<Integer, Integer> snakesAndLadders;

    SnakesAndLadders() {
        snakesAndLadders = new HashMap<>();
        snakesAndLadders.put(5, 8);
        snakesAndLadders.put(12, 7);
        snakesAndLadders.put(23, 19);
        snakesAndLadders.put(16, 10);
        snakesAndLadders.put(7, 15);
    }

    public int newTile(Player player) {
        int gamli = player.getTile();
        int nyi = snakesAndLadders.getOrDefault(gamli, 0);
        if (nyi == 0) {
            return gamli;
        } else if (nyi > gamli) {
            player.setMessage(player.getName() + " fór upp stigann!");
        } else {
            player.setMessage(player.getName() + " fór niður snákinn :(");
        }
        return nyi;
    }

    public HashMap<Integer, Integer> getSnakesAndLadders() {
        return snakesAndLadders;
    }
}
