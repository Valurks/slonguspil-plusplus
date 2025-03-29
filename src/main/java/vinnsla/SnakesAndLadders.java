package vinnsla;

import java.util.HashMap;

public class SnakesAndLadders {

    private final HashMap<Integer, Integer> snakesAndLadders;
    private int difficulty = 1;
    private int max;

    SnakesAndLadders() {
        snakesAndLadders = new HashMap<>();
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
            System.out.println("Board ssize not set");
            return;
        }
        // fyrri hluti er stærð borðs/6  seinni hlutinn skilar random tölu milli -max/9 til max/9
        double randomPosNeg = Math.random()*difficulty*2-1;
        System.out.println(randomPosNeg);
        int numberOfSL = (int) (difficulty*max/4.0 + randomPosNeg*max/12);
        for (int i = 0; i < numberOfSL; i++ ) {
            insert();
        }
    }

    private void insert() {
        int tile = findValidTile();
        int destination = findValidDestinaton(tile);
        System.out.println(tile +" "+ destination);
        snakesAndLadders.put(tile, destination);
    }

    private int findValidTile() {
        int tile = (int) (Math.random()*(max-3)+2);
        boolean valid = true;
        if(snakesAndLadders.containsKey(tile) || snakesAndLadders.containsValue(tile)) {
            return findValidTile();
        }
        return tile;
    }

    private int findValidDestinaton(int tile) {
        boolean direction = Math.random() > difficulty*0.4; //true upp, false niður
        int length = (int) (Math.random()*difficulty*5+2); //lengd stiga/snáks
        length = direction ? length : -length;
        int destination = Math.min(Math.max(tile + length,1), max);

        if (snakesAndLadders.containsValue(destination) || snakesAndLadders.containsKey(destination)) {
             return findValidDestinaton(tile);
         }
        return destination;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
        createMap();
    }

    public void setSize(int max) {
        this.max = max;
        createMap();
    }

}
