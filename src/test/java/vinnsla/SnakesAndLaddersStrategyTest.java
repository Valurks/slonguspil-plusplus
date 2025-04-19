package vinnsla;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Class that tests the behvior of SnakesAndLaddersStrategy class.
 */
public class SnakesAndLaddersStrategyTest {
    private SnakesAndLaddersStrategy strategy;
    private List<BoardConnections> connections;
    /**
     * Sets up default values to test.
     * Strategy is of class SnakesAndLaddersStrategy.
     * Connections is a list of 100 BoardConnections with difficulty 1.
     */
    @Before
    public void setUp() {
        strategy = new SnakesAndLaddersStrategy();
        connections = strategy.generate(1.0, 100);
    }

    /**
     * Tests that the generate method works.
     */
    @Test
    public void testGenerateCreatesConnections() {
        assertNotNull("Connections list should not be null", connections);
        assertFalse("Connections list should not be empty", connections.isEmpty());
    }

    /**
     * Tests that connection types are correct in connections.
     */
    @Test
    public void testConnectionTypes() {

        for (BoardConnections connection : connections) {
            if (connection.to() > connection.from()) {
                assertEquals(ConnectionType.LADDER, connection.type());
            } else {
                assertEquals(ConnectionType.SNAKE, connection.type());
            }
        }
    }

    /**
     * Tests generating a list of no connections.
     */
    @Test
    public void testGeneratingNoConnections(){
        connections = strategy.generate(1,0);
        assertTrue(connections.isEmpty());
    }
}
