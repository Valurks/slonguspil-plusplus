package vidmot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import vinnsla.Dice;
import vinnsla.Game;
import vinnsla.Player;

public class SlangaController {

    private int rows;
    private int cols;

    public ImageView fxDice;
    public Button fxButton;
    public VBox fxLabels;
    @FXML
    private GridPane fxGrid;

    private Game game = new Game(rows, cols, 1.0);
    private Dice dice;
    private final SettingsDialogController settingsDialog =
        new SettingsDialogController();
    private double[] settings = new double[]{2.0,24.0,1.0};
    private String[] playerNames = new String[4];
    private boolean[] bots = new boolean[4];
    private Player[] players;
    private Image[] icons = GifLoader.getIcons();
    private ImageView[] playerIcons;

    private boolean finished = false;

    ArrayList<Label> labels = new ArrayList<>();

    public void initialize() {
        Arrays.fill(playerNames, "Ónefndur leikmaður");
        Arrays.fill(bots, false);
        findValidBoardSize((int) settings[1]);
        settingsHandler();
        createGame();
    }

    private void createLabels() {
        fxLabels.getChildren().clear();
        Label[] playerLabels = new Label[(int) settings[0]];
        for (int i = 0; i < playerLabels.length; i++ ) {
            playerLabels[i] = new Label(playerNames[i]);
        }
        HBox upper = new HBox();
        HBox lower = new HBox();

        if (settings[0] == 2) {
            upper.getChildren().add(playerLabels[0]);
            lower.getChildren().add(playerLabels[1]);
        } else {
            upper.getChildren().addAll(playerLabels[0],playerLabels[1]);
            for (int i = 2; i < playerLabels.length; i++) {
                lower.getChildren().add(playerLabels[i]);
            }
        }
        for (int i = 0; i < settings[0]; i++) {
            playerLabels[i].textProperty().bind(players[i].getMessage());
        }
        fxLabels.getChildren().addAll(upper, lower);
    }

    private int settingsHandler() {
        settingsDialog.open();
        if(settingsDialog.getResult()[0][0]==null) return 1;
        settings = new double[3];
        playerNames = new String[4];
        bots = new boolean[4];
        for (int i = 0; i < 4; i++) {
            if (i < 3) {
                settings[i] = Double.parseDouble(
                    settingsDialog.getResult()[0][i]
                );
            }
            playerNames[i] = settingsDialog.getResult()[1][i];
            bots[i] = Boolean.parseBoolean(settingsDialog.getResult()[2][i]);
        }
        findValidBoardSize((int) settings[1]);
        return 0;
    }

    public void createGame() {
        game = new Game(rows, cols, settings[2]);
        for (int i = 0; i < settings[0]; i++) {
            game.addPlayer(playerNames[i], bots[i], i);
        }
        players = game.getPlayers();
        game.newGame();
        dice = game.getDice();
        playerIcons = new ImageView[4];
        createGrid();
        createListener();
        createPlayers();
        setPlayersPos();

        game
            .botTurnProperty()
            .addListener((obs, oldVal, newVal) -> {
                fxDice.setMouseTransparent(newVal);
            });
        game.setOnBotTurn(() -> Platform.runLater(this::diceHandler));

        visualSnakesLadders();
        createLabels();
        updatePosition(players[0],playerIcons[0]);
        fxGrid.getWidth();
    }

    private void createPlayers() {
        for (int i = 0; i < settings[0]; i++) {
            ImageView currentIcon = new ImageView(icons[i]);
            playerIcons[i] = currentIcon;
            GridPane.setHalignment(currentIcon, HPos.CENTER);
            GridPane.setValignment(currentIcon, VPos.CENTER);
            currentIcon.setFitHeight(50);
            currentIcon.setFitWidth(50);
            currentIcon.pickOnBoundsProperty();
            currentIcon.preserveRatioProperty();
            fxGrid.getChildren().add(currentIcon);
            playerDisplayListener(players[i], currentIcon);

        }
    }

    private void findValidBoardSize(int size) {
        rows = (int) Math.round(Math.sqrt(size)-0.9);
        cols = (int) Math.round((float) size / rows);
        settings[1] = rows*cols;
    }

    /**
     * Núna hægt að breyta gridpane
     */
    private void setPlayersPos() {
        Label label = labels.getFirst();
        int startCol = GridPane.getColumnIndex(label);
        int startRow = GridPane.getRowIndex(label);
        for (int i = 0; i < settings[0]; i++) {
            GridPane.setColumnIndex(playerIcons[i], startCol);
            GridPane.setRowIndex(playerIcons[i], startRow);
        }
    }

    public void createGrid() {
        fxGrid.getChildren().clear();
        String[] colors = new String[] {
            "FF8080", "F6FDC3", "FFCF96", "CDFAD5",
        };
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int col = (i % 2 == 0) ? j : cols - j - 1; // Ef oddatölu röð -> byrja fra hægri
                int row = rows - i - 1; // Byrja niðri
                int index = i * cols + j + 1;
                labels.add(new Label(index + ""));
                Label label = labels.get(index - 1);
                label.setStyle(
                    "-fx-background-color: #" +
                    colors[(int) (Math.random() * colors.length)] +
                    ";"
                );
                fxGrid.add(label, col, row);
            }
        }
    }

    public void createListener() {
        dice
            .getProp()
            .addListener(event ->
                fxDice.setImage(
                    new Image(
                        Objects.requireNonNull(
                            getClass()
                                .getResourceAsStream(
                                    "images/dice/" + dice.getNumber() + ".png"
                                )
                        )
                    )
                )
            );
    }

    public void diceHandler() {
        int utkoma = game.round();
        if (utkoma == -1 || finished) {
            nyrLeikurHandler();
        } else if (utkoma == 1) {
            String winner =
                game.getNextPlayer().getName() + " er kominn í mark!";
            game.getNextPlayer().setMessage(winner);
            finished = true;
        }
    }

    public void nyrLeikurHandler() {
        if (settingsHandler()!=0) {
            return;
        }
        finished = false;
        buttonReleased();
        createGame();
        for (int i = 0; i < settings[0]; i++) {
            players[i].setMessage("");
        }
    }

    public void buttonClicked() {
        fxButton.setStyle("-fx-background-color: grey;");
    }

    public void buttonReleased() {
        fxButton.setStyle("-fx-background-color: transparent;");
    }

    public void playerDisplayListener(Player player, ImageView icon) {
        player
            .getTileProp()
            .addListener((observable, oldValue, newValue) -> {
                Label label = labels.get(newValue.intValue() - 1);
                int col = GridPane.getColumnIndex(label);
                int row = GridPane.getRowIndex(label);
                GridPane.setColumnIndex(icon, col);
                GridPane.setRowIndex(icon, row);
                updatePosition(player, icon);
            });
    }

    public void updatePosition(Player player, ImageView icon) {
            int offset = 0;
            for (int j = 0; j < settings[0]; j++) {
                if (
                        player.getTile() == players[j].getTile() &&
                                icon != playerIcons[j]
                ) {
                    offset++;
                }
            }
            if (offset > 0) {
                ArrayList<ImageView> iconsOnTile = new ArrayList<>();
                for (int j = 0; j < settings[0]; j++) {
                    if (player.getTile() == players[j].getTile()) {
                        iconsOnTile.add(playerIcons[j]);
                    }
                }
                for (int j = 0; j < iconsOnTile.size(); j++) {
                    iconsOnTile.get(iconsOnTile.size()-j-1).toFront();
                    iconsOnTile.get(j).setTranslateX((double) (-offset * 10) /2+10*j);
                }
            } else {
                icon.setTranslateX(0);
                icon.toFront();
            }
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
            view.setImage(
                new Image(
                    getClass().getResourceAsStream("images/" + img + ".png")
                )
            );
            fxGrid.add(view, col, row);
        }
    }
}
