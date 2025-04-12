package vinnsla;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.util.Duration;

import java.util.HashMap;

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

    public Game(int height, int width, double difficulty) {
        BehaviorStrategy strategy = new SnakesAndLaddersStrategy();
        max = height * width;
        boardBehavior = new BoardBehavior(strategy,difficulty, max);
    }

    public Player[] getPlayers() {
        return players;
    }

    public Dice getDice() {
        return dice;
    }

    public void addPlayer(String name, boolean isBot, int index) {
        Player player = new Player(name, max, isBot);
        players[index] = player;
    }

    public Player getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(Player player) {
        nextPlayer = player;
    }

    public HashMap<Integer, Integer> getSnakesAndLaddersMap() {
        return boardBehavior.getSnakesAndLadders();
    }

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

    public int round() {
        if (nextPlayer == null) return -1;
        dice.throwDice();
        if (nextPlayer.move(dice.getNumber())) return 1;
        else {
            nextPlayer.setTile(boardBehavior.newTile(nextPlayer));
        }

        int i = (++currentPlayerIndex) % numberOfPlayers;
        nextPlayer = players[i];

        //nextPlayer = nextPlayer == player1 ? player2 : player1;
        if (nextPlayer.isBot())
            handleBotRound();
        return 0;
    }

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

    public SimpleBooleanProperty botTurnProperty() {
        return botTurn;
    }

    public void setOnBotTurn(Runnable onBotTurn) {
        this.onBotTurn = onBotTurn;
    }

    public BoardBehavior getSnakesAndLadders() {
        return boardBehavior;
    }

}
