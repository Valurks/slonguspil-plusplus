package vidmot;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import vinnsla.Player;
import vinnsla.Game;
import vinnsla.Dice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class SlangaController {
    private static final int GRID_ROW = 3;
    private static final int GRID_COL = 3;

    public ImageView fxDice;
    public Button fxButton;
    public VBox fxLabelContainer;
    public ImageView fxPlayer1Dislpay;
    public ImageView fxPlayer2Dislpay;
    @FXML
    private GridPane fxGrid;
    @FXML
    private Label fxLabel1;
    @FXML
    private Label fxLabel2;

    private final Game game = new Game(GRID_ROW, GRID_COL);
    private final Dice dice = game.getDice();
    private final Player player1 = game.getPlayer1();
    private final Player player2 = game.getPlayer2();

    private boolean finished;

    ArrayList<Label> labels = new ArrayList<>();

    public void initialize() {
        createGrid();
        createListener();
        setPlayersPos();

        visualSnakesLadders();

        fxLabel1.textProperty().bind(player1.getMessage());
        fxLabel2.textProperty().bind(player2.getMessage());

        playerDisplayListener(player1, fxPlayer1Dislpay);
        playerDisplayListener(player2, fxPlayer2Dislpay);

        fxPlayer2Dislpay.toFront();
        fxPlayer1Dislpay.toFront();
    }

    /**
     * Núna hægt að breyta gridpane
     */
    private void setPlayersPos() {
        Label label = labels.getFirst();
        int startCol = GridPane.getColumnIndex(label);
        int startRow = GridPane.getRowIndex(label);
        GridPane.setColumnIndex(fxPlayer1Dislpay,startCol);
        GridPane.setRowIndex(fxPlayer1Dislpay,startRow);
        GridPane.setRowIndex(fxPlayer2Dislpay,startCol);
        GridPane.setRowIndex(fxPlayer2Dislpay,startRow);
    }

    public void createGrid() {
        String[] colors = new String[]{"FF8080", "F6FDC3", "FFCF96", "CDFAD5"};
        for (int i = 0; i < GRID_ROW; i++) {
            for (int j = 0; j < GRID_COL; j++) {
                int col = (i % 2 == 0) ? j : GRID_COL - j - 1; // Ef oddatölu röð -> byrja fra hægri
                int row = GRID_ROW - i - 1; // Byrja niðri
                int index = i * GRID_COL + j + 1;
                labels.add(new Label(index + ""));
                Label label = labels.get(index - 1);
                label.setStyle("-fx-background-color: #" + colors[(int) (Math.random() * colors.length)] + ";");
                fxGrid.add(label, col, row);

            }
        }
    }

    public void createListener() {
        dice.getProp().addListener((obs, oldValue, newValue)
                -> fxDice.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/dice/" + newValue.intValue() + ".png")))));
    }

    public void diceHandler() {
        int utkoma = game.round();
        if (utkoma == -1 || finished) {
            nyrLeikurHandler();
        } else if (utkoma == 1) {
            String winner = game.getNextPlayer().getName() + " er kominn í mark!";
            game.getNextPlayer().setMessage(winner);
            finished = true;
        }
    }

    public void nyrLeikurHandler() {
        finished = false;
        buttonReleased();
        game.newGame();
        player1.setMessage("");
        player2.setMessage("");
        namePopUp();

    }

    public void namePopUp() {
        DialogController dialog1 = new DialogController(player1);
        dialog1.showDialog();

        DialogController dialog2 = new DialogController(player2);
        dialog2.showDialog();
    }

    public void buttonClicked() {
        fxButton.setStyle("-fx-background-color: grey;");
    }

    public void buttonReleased() {
        fxButton.setStyle("-fx-background-color: transparent;");
    }

    public void playerDisplayListener(Player player, ImageView display) {
        player.getTileProp().addListener((observable, oldValue, newValue) -> {
            Label label = labels.get(newValue.intValue() - 1);
            int col = GridPane.getColumnIndex(label);
            int row = GridPane.getRowIndex(label);
            GridPane.setColumnIndex(display, col);
            GridPane.setRowIndex(display, row);
        });
    }

    public void visualSnakesLadders() {
        HashMap<Integer, Integer> map = game.getSlongurStigar();
        for (int key : map.keySet()) {
            String img;
            int size;
            if (map.get(key) > key) {
                img = "ladder";
                size = 25;
            } else {
                img = "snake";
                size = 20;
            }

            Label label = labels.get(key - 1);
            int col = GridPane.getColumnIndex(label);
            int row = GridPane.getRowIndex(label);

            ImageView view = new ImageView();
            view.setFitHeight(size);
            view.setFitWidth(size);
            GridPane.setValignment(view, VPos.BOTTOM);
            GridPane.setHalignment(view, HPos.RIGHT);
            view.setImage(new Image(getClass().getResourceAsStream("images/" + img + ".png")));
            fxGrid.add(view, col, row);
        }
    }
}
