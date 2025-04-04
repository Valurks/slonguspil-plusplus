package vinnsla;

import java.util.ArrayList;
import java.util.HashMap;

public class SnakesAndLadders {

    private final HashMap<Integer, Integer> snakesAndLadders;
    private final double difficulty;
    private int max;
    private int recursion = 0;
    ArrayList<Integer> availableTiles= new ArrayList<>();

    SnakesAndLadders(double difficulty, int max) {
        snakesAndLadders = new HashMap<>();
        this.difficulty = difficulty;
        this.max = max;
        createMap();
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

    public void createMap() {
        if (max == 0) {
            System.out.println("Board size not set");
            return;
        }
        for (int i = 1;i <= max ;i++ ) {
            availableTiles.add(i);
        }
        // randomPosNeg er random tala milli -1 og 1
        double randomPosNeg = Math.random()*difficulty*2-1;
        // fjöldi snáka/stiga
        int numberOfSL = (int) (difficulty*max/5.0 + randomPosNeg*Math.sqrt((double) max /2));
        numberOfSL = Math.min(numberOfSL,(int) (max/2.5));
        for (int i = 0; i < numberOfSL; i++ ) {
            insert();
        }
    }

    private void insert() {
        int destination = -1;
        int tile = 0;
        while( destination == -1) {
            tile = findValidTile();
            destination = findValidDestinaton(tile);
        }
            availableTiles.remove(Integer.valueOf(tile));
            availableTiles.remove(Integer.valueOf(destination));
            snakesAndLadders.put(tile, destination);
    }

    private int findValidTile() {
        int tile = availableTiles.get((int)(Math.random() * availableTiles.size()));
        if(snakesAndLadders.containsKey(tile) || snakesAndLadders.containsValue(tile)) {
            return findValidTile();
        }
        return tile;
    }

    private int findValidDestinaton(int tile) {
        if (recursion == 25) {
            recursion = 0;
            return -1;
        } else {
            recursion++;
        }

        boolean direction = Math.random() > Math.pow(difficulty,1.4)*0.4; //true upp, false niður
        int length = (int) (Math.random()*difficulty*5+2); //lengd stiga/snáks
        length = direction ? length : -length;
        int destination = Math.min(Math.max(tile + length,1), max);
        if (recursion == 25) {
            return -1;
        }

        if (snakesAndLadders.containsValue(destination) || snakesAndLadders.containsKey(destination)) {
             return findValidDestinaton(tile);
         }
        return destination;
    }
}
