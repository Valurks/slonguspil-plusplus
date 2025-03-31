package vinnsla;

import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.util.Duration;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Game {

    private final Dice dice = new Dice();
    private final Player player1;
    private final Player player2;
    private final SnakesAndLadders snakesAndLadders;
    private Player nextPlayer;
    private int difficulty;

    private final ArrayList<Player> players = new ArrayList<>();
    private int currentPlayerIndex = 0;
    private Runnable onBotTurn;
    private final SimpleBooleanProperty botTurn = new SimpleBooleanProperty(false);


    public Game(int height, int width) {
        int max = height * width;
        player1 = new Player("Ónefndur Leikmaður", max, false);
        player2 = new Player("Ónefndur Leikmaður", max, false);
        Player player3 = new Player("player3",max, true);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        snakesAndLadders = new SnakesAndLadders();
        snakesAndLadders.setSize(max);
    }
    public ArrayList<Player> getPlayers(){
        return players;
    }
    public Dice getDice() {
        return dice;
    }
    public void addPlayer(Player player){
        players.add(player);
    }
    public Player getNextPlayer() {
        return nextPlayer;
    }

    public HashMap<Integer, Integer> getSlongurStigar() {
        return snakesAndLadders.getSnakesAndLadders();
    }

    public void newGame() {
        for (Player player: players){
            player.setTile(1);
        }
        nextPlayer = players.getFirst();

    }

    public int round() {
        if (nextPlayer == null) return -1;
        dice.throwDice();
        if (nextPlayer.move(dice.getNumber())) return 1;
        else {
            nextPlayer.setTile(snakesAndLadders.newTile(nextPlayer));
        }

        int i = (++currentPlayerIndex) % players.size();
        nextPlayer = players.get(i);

        //nextPlayer = nextPlayer == player1 ? player2 : player1;
        if(nextPlayer.isBot())
            handleBotRound();


        return 0;
    }

    private void handleBotRound() {
        botTurn.set(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            onBotTurn.run();
            botTurn.set(false);
        });

        pause.play();
    }
    public SimpleBooleanProperty botTurnProperty(){
        return botTurn;
    }
    public void setOnBotTurn(Runnable onBotTurn){
        this.onBotTurn = onBotTurn;
    }



    public static void main(String[] args) {
        Game game = new Game(4, 6);
        game.newGame();
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        System.out.print("Á næsti leikmaður að gera? ");
        String svar = scanner.next();
        while ("j".equalsIgnoreCase(svar)) {
            if (game.round() == 1) {
                System.out.println(game.nextPlayer.getName() + " er kominn í mark");
                return;
            }
            System.out.print("Á næsti leikmaður að gera?");
//            svar = scanner.next();
        }

    }
}
