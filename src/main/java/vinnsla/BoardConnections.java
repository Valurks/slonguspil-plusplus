package vinnsla;

/**
 * Record class that keeps track of all connections.
 * @param from The start of the connection.
 * @param to The end of the connection.
 * @param type The type of the connection.
 */
public record BoardConnections(int from, int to, ConnectionType type) {
}
