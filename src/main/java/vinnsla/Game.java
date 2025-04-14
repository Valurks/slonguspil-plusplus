package vinnsla;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.util.Duration;

import java.util.HashMap;

/**
 * The class representing the current game.
 */
public class Game {

    private Dice dice;
    private final BoardBehavior boardBehavior;
    private Player nextPlayer;
    private final int max;
    private int numberOfPlayers;

    private final Player[] players = new Player[4];
    private int currentPlayerIndex = 0;
    private Runnable onBotTurn;
    private final SimpleBooleanProperty botTurn = new SimpleBooleanProperty(false);

    /**
     * Constructs a new game object.
     * @param rows The amount of rows.
     * @param cols The amount of cols.
     * @param difficulty The current difficulty.
     */
    public Game(int rows, int cols, double difficulty) {
        BehaviorStrategy strategy = new SnakesAndLaddersStrategy();
        max = rows * cols;
        boardBehavior = new BoardBehavior(strategy,difficulty, max);
    }

    /**
     * Returns all current players.
     * @return an array of players.
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Returns the current dice.
     * @return the Dice object.
     */
    public Dice getDice() {
        return dice;
    }

    /**
     * Creates a new player and adds it to players.
     * @param name The name of the player.
     * @param isBot Boolean value, true if player is a bot.
     * @param index current index of player, from 0 to 3.
     */
    public void addPlayer(String name, boolean isBot, int index) {
        Player player = new Player(name, max, isBot);
        players[index] = player;
    }

    /**
     * Returns the next player.
     * @return The Player object of the upcoming player.
     */
    public Player getNextPlayer() {
        return nextPlayer;
    }

    /**
     * Sets the next player.
     * @param player The next player.
     */
    public void setNextPlayer(Player player) {
        nextPlayer = player;
    }

    /**
     * Returns the map of all connections.
     * @return HashMap of integer values that represent connections.
     */
    public HashMap<Integer, Integer> getConnectionMap() {
        return boardBehavior.getConnectionMap();
    }

    /**
     * Creates a new game and sets default values.
     */
    public void newGame() {
        dice = new Dice();
        numberOfPlayers = 0;
        for (Player player : players) {
            if (player != null) {
                player.setTile(1);
                numberOfPlayers++;
            }
        }
        nextPlayer = players[0];
    }

    /**
     * Plays a new round and updates next player.
     * @return -1 if there is no nextplayer.
     *          1 if nextplayer has won.
     *          0 if the game should continue.
     */
    public int round() {
        if (nextPlayer == null) return -1;
        dice.throwDice();
        if (nextPlayer.move(dice.getNumber())) return 1;
        else {
            nextPlayer.setTile(boardBehavior.newTile(nextPlayer));
        }

        int i = (++currentPlayerIndex) % numberOfPlayers;
        nextPlayer = players[i];

        if (nextPlayer.isBot())
            handleBotRound();
        return 0;
    }

    /**
     * Handles round when bot plays.
     */
    private void handleBotRound() {
        botTurn.set(true);
        Platform.runLater(() -> {
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> {
                onBotTurn.run();
                botTurn.set(false);
            });

            pause.play();
        });
    }

    /**
     * Returns property of whether bot is playing.
     * @return The SimpleBooleanProperty that represents if bot is playing.
     */
    public SimpleBooleanProperty botTurnProperty() {
        return botTurn;
    }

    /**
     * Sets the action to be executed when its bot's turn.
     * @param onBotTurn A Runnable that defines what happens when its bot's turn.
     */
    public void setOnBotTurn(Runnable onBotTurn) {
        this.onBotTurn = onBotTurn;
    }

    /**
     * Returns the current BoardBehavior.
     * @return The BoardBehavior that manages the connections on the board.
     */
    public BoardBehavior getBoardBehavior() {
        return boardBehavior;
    }

}
