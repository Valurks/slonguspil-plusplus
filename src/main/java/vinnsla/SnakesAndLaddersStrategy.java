package vinnsla;

import java.util.ArrayList;
import java.util.List;

/**
 * Nafn: Hjörleifur Örn Sveinsson
 * Gmail: hjorleifursveins@gmail.com
 * Lýsing:
 */
public class SnakesAndLaddersStrategy implements BehaviorStrategy{
    private final int MAX_RECURSION = 25;
    private final double MAX_CONNECTION_RATIO = 2.5;
    private double difficulty;
    private int max;
    private int recursion = 0;
    private ArrayList<Integer> availableTiles;
    private List<BoardConnections> connections;
    @Override
    public List<BoardConnections> generate(double difficulty, int max){
        this.difficulty = difficulty;
        this.max = max;
        this.availableTiles = new ArrayList<>();
        this.connections = new ArrayList<>();
        createMap();
        return connections;
    }

    public void createMap() {
        if (max == 0) {
            System.out.println("Board size not set");
            return;
        }
        for (int i = 1; i <= max; i++) {
            availableTiles.add(i);
        }
        availableTiles.removeFirst();
        availableTiles.removeLast();
        double randomPosNeg = Math.random() * difficulty * 2 - 1;
        int numberOfConnections = (int) (difficulty * max / 5.0 + randomPosNeg * Math.sqrt((double) max / 2));
        numberOfConnections = Math.min(numberOfConnections, (int) (max / MAX_CONNECTION_RATIO));
        for (int i = 0; i < numberOfConnections; i++) {
            insert();
        }
    }

    private void insert() {
        int destination = -1;
        int tile = 0;
        while (destination == -1) {
            tile = findValidTile();
            destination = findValidDestinaton(tile);
        }
        availableTiles.remove(Integer.valueOf(tile));
        availableTiles.remove(Integer.valueOf(destination));
        ConnectionType type = destination > tile ? ConnectionType.LADDER : ConnectionType.SNAKE;
        connections.add(new BoardConnections(tile, destination, type));
    }

    private int findValidTile() {
        int tile = availableTiles.get((int) (Math.random() * availableTiles.size()));
        for (BoardConnections connections : connections){
            if (connections.to() == tile || connections.from() == tile)
                return findValidTile();
        }
        return tile;
    }

    private int findValidDestinaton(int tile) {
        if (++recursion >= MAX_RECURSION) {
            recursion = 0;
            return -1;
        }
        boolean direction = Math.random() > Math.pow(difficulty, 1.4) * 0.4; //true upp, false niður
        int length = (int) (Math.random() * difficulty * 5 + 2); //lengd stiga/snáks
        length = direction ? length : -length;
        int destination = Math.min(Math.max(tile + length, 1), max);

        for (BoardConnections connections : connections){
            if (connections.to() == destination || connections.from() == destination)
                return findValidDestinaton(tile);
        }
        return destination;
    }
}
