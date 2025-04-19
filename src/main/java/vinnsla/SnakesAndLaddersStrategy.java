package vinnsla;

import java.util.ArrayList;
import java.util.List;

/**
 * Strategy class for generating snakes and ladders on a game board.
 */
public class SnakesAndLaddersStrategy implements BehaviorStrategy {
    private final int MAX_RECURSION = 25;
    private final double MAX_CONNECTION_RATIO = 2.5;
    private double difficulty;
    private int max;
    private int recursion = 0;
    private ArrayList<Integer> availableTiles;
    private List<BoardConnections> connections;

    /**
     * Generates a list of board connections.
     */
    @Override
    public List<BoardConnections> generate(double difficulty, int max) {
        this.difficulty = difficulty;
        this.max = max;
        this.availableTiles = new ArrayList<>();
        this.connections = new ArrayList<>();
        createMap();
        return connections;
    }

    /**
     * Initializes the board connections and creates the map.
     */
    public void createMap() {
        if (max == 0) {
            System.out.println("Board size not set");
            return;
        }
        for (int i = 2; i < max; i++) {
            availableTiles.add(i);
        }
        double randomPosNeg = Math.random() * difficulty * 2 - 1;
        int numberOfConnections = (int) (difficulty * max / 5.0 + randomPosNeg * Math.sqrt((double) max / 2));
        numberOfConnections = Math.min(numberOfConnections, (int) (max / MAX_CONNECTION_RATIO));
        for (int i = 0; i < numberOfConnections; i++) {
            insert();
        }
    }

    /**
     * Inserts a single snake or ladder into the map.
     */
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

    /**
     * Finds a tile that has not been used in a connection yet.
     *
     * @return A valid tile number.
     */
    private int findValidTile() {
        int tile = availableTiles.get((int) (Math.random() * availableTiles.size()));
        for (BoardConnections connections : connections) {
            if (connections.to() == tile || connections.from() == tile)
                return findValidTile();
        }
        return tile;
    }

    /**
     * Finds a valid destination tile for a current tile.
     *
     * @param tile The current tile.
     * @return The valid destination tile.
     * -1 if MAX_RECURSION is hit.
     */
    private int findValidDestinaton(int tile) {
        if (++recursion >= MAX_RECURSION) {
            recursion = 0;
            return -1;
        }
        boolean isLadder = Math.random() > Math.pow(difficulty, 1.4) * 0.4;
        int connectionLength = (int) (Math.random() * difficulty * 5 + 2);
        connectionLength = isLadder ? connectionLength : -connectionLength;
        int destination = Math.min(Math.max(tile + connectionLength, 1), max - 1);

        for (BoardConnections connections : connections) {
            if (connections.to() == destination || connections.from() == destination)
                return findValidDestinaton(tile);
        }
        return destination;
    }
}
