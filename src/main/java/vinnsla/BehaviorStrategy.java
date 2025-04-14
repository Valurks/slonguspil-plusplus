package vinnsla;

import java.util.List;

/**
 * Strategy interface for board generation.
 */
public interface BehaviorStrategy {
    /**
     * Get the generated board based on BoardConnections.
     * @param difficulty The current difficulty.
     * @param max The max number of tiles.
     * @return A list of BoardConnections.
     */
    List<BoardConnections> generate(double difficulty, int max);
}
