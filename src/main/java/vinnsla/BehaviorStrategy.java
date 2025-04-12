package vinnsla;

import java.util.List;

/**
 * Nafn: Hjörleifur Örn Sveinsson
 * Gmail: hjorleifursveins@gmail.com
 * Lýsing:
 */
public interface BehaviorStrategy {
    List<BoardConnections> generate(double difficulty, int max);
}
