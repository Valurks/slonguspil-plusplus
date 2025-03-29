package vinnsla;

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

    public Game(int height, int width) {
        int max = height * width;
        player1 = new Player("Ónefndur Leikmaður", max);
        player2 = new Player("Ónefndur Leikmaður", max);
        Player player3 = new Player("player3",max);
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

    public Player getNextPlayer() {
        return nextPlayer;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public HashMap<Integer, Integer> getSlongurStigar() {
        return snakesAndLadders.getSnakesAndLadders();
    }

    public void newGame() {
        for (Player player: players){
            player.setTile(1);
        }
        nextPlayer = player1;

    }

    public int round() {
        if (nextPlayer == null) return -1;
        dice.throwDice();
        if (nextPlayer.move(dice.getNumber())) return 1;
        else {
            nextPlayer.setTile(snakesAndLadders.newTile(nextPlayer));
        }
        currentPlayerIndex++;
        int i = (currentPlayerIndex) % players.size();
        nextPlayer = players.get(i);
        //nextPlayer = nextPlayer == player1 ? player2 : player1;
        return 0;
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
