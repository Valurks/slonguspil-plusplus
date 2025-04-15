package vinnsla;

import java.util.HashMap;
import java.util.List;

/**
 * A class that manages the behavior of the board.
 */
public class BoardBehavior {

    private List<BoardConnections> connections;
    private final HashMap<Integer, Integer> connectionMap;

    /**
     * Constructs a new board behavior with specified strategy, difficulty and max.
     * @param strategy The strategy used.
     * @param difficulty The current difficulty,
     * @param max The number of max tiles.
     */
    BoardBehavior(BehaviorStrategy strategy, double difficulty, int max) {
        connections = strategy.generate(difficulty, max);
        connectionMap = new HashMap<>();
        for (BoardConnections connection : connections) {
            connectionMap.put(connection.from(), connection.to());
        }
    }

    /**
     * Sets the message based on what type of connection.
     * @param player The current player
     * @return the number of the new tile that player goes to.
     */
    public int newTile(Player player) {

        int currentTile = player.getTile();
        int newTile = connectionMap.getOrDefault(currentTile,currentTile);

        if (connectionMap.containsKey(currentTile)) {
            if (newTile > currentTile) {
                player.setMessage(player.getName() + " climbed the ladder! \uD83E\uDE9C");
            } else {
                player.setMessage(player.getName() + " slid down the snake \uD83D\uDC0D");
            }
        }
        return newTile;
    }

    /**
     * Returns the connection map of the board.
     * @return The connection map.
     */
    public HashMap<Integer, Integer> getConnectionMap() {
        return connectionMap;
    }

}
