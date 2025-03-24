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

    private final Game game = new Game(4, 6);
    private final Dice dice = game.getDice();
    private final Player player1 = game.getPlayer1();
    private final Player player2 = game.getPlayer2();

    private boolean finished;

    ArrayList<Label> labels = new ArrayList<>();

    public void initialize() {
        createGrid();
        createListener();

        visualSnakesLadders();

        fxLabel1.textProperty().bind(player1.getMessage());
        fxLabel2.textProperty().bind(player2.getMessage());

        playerDisplayListener(player1, fxPlayer1Dislpay);
        playerDisplayListener(player2, fxPlayer2Dislpay);

        fxPlayer2Dislpay.toFront();
        fxPlayer1Dislpay.toFront();
    }

    public void createGrid() {
        String[] colors = new String[]{"FF8080", "F6FDC3", "FFCF96", "CDFAD5"};
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                int index = i * 6 + j + 1;
                labels.add(new Label(index + ""));
                Label label = labels.get(index - 1);
                label.setStyle("-fx-background-color: #" + colors[(int) (Math.random() * colors.length)] + ";");
                fxGrid.add(label, j, 4 - i);
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
