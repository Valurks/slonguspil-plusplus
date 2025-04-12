package vinnsla;

import java.util.HashMap;
import java.util.List;

public class BoardBehavior {

    private List<BoardConnections> connections;
    private final HashMap<Integer,Integer> connectionMap;

    BoardBehavior(BehaviorStrategy strategy, double difficulty, int max) {
       connections = strategy.generate(difficulty,max);
       connectionMap = new HashMap<>();
        for (BoardConnections connection : connections){
            connectionMap.put(connection.from(), connection.to());
        }
    }

    public int newTile(Player player) {

        int currentTile = player.getTile();
        int newTile = connectionMap.getOrDefault(currentTile,currentTile);

       if (newTile > currentTile) {
            player.setMessage(player.getName() + " fór upp stigann!");
        } else {
            player.setMessage(player.getName() + " fór niður snákinn :(");
        }
        return newTile;
    }

    public HashMap<Integer, Integer> getSnakesAndLadders() {
        return connectionMap;
    }

}
