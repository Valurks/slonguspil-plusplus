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

/**
 * Main controller class for the SlönguSpil++ game.
 */
public class SlangaController {

    public static final int MAX_PLAYERS = 4;
    public static final int SIZE_OF_ICONS = 50;

    private int rows;
    private int cols;
    private Game game = new Game(rows, cols, 1.0);
    private Dice dice;
    private final SettingsDialogController settingsDialog = new SettingsDialogController();
    private boolean finished = false;

    private double[] settings = new double[]{2.0, 24.0, 1.0};
    private String[] playerNames = new String[MAX_PLAYERS];
    private boolean[] bots = new boolean[MAX_PLAYERS];
    private Player[] players;
    private final Image[] icons = GifCache.getIcons();
    private ImageView[] playerIcons;
    private Label[] playerLabels;
    ArrayList<Label> gridLabels = new ArrayList<>();

    @FXML
    private ImageView fxDice;
    @FXML
    private Button fxButton;
    @FXML
    private VBox fxLabels;
    @FXML
    private GridPane fxGrid;

    /**
     * Initalizes the controller class with default values.
     */
    public void initialize() {
        Arrays.fill(playerNames, "Unnamed player");
        Arrays.fill(bots, false);
        findValidBoardSize((int) settings[1]);
        settingsHandler();
        setSettingsIcon();
        createGame();
    }

    /**
     * Sets up the settings wheel with hovering affects.
     */
    private void setSettingsIcon() {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/vidmot/images/backgrounds/settings.png")));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(70);
        imageView.setFitHeight(70);
        fxButton.setGraphic(imageView);
        fxButton.setOnMouseEntered(e -> {
            fxButton.setScaleX(1.1);
            fxButton.setScaleY(1.1);
        });

        fxButton.setOnMouseExited(e -> {
            fxButton.setScaleX(1.0);
            fxButton.setScaleY(1.0);
        });
    }

    /**
     * Creates the gridLabels of the players.
     */
    private void createLabels() {
        fxLabels.getChildren().clear();
        playerLabels = new Label[(int) settings[0]];
        for (int i = 0; i < playerLabels.length; i++) {
            playerLabels[i] = new Label(playerNames[i]);
        }
        String[] colors = new String[]{"2994EF", "EA1D36", "f8ba00", "00d900"};

        HBox upperBox = new HBox();
        HBox lowerBox = new HBox();

        if (settings[0] == 2) {
            upperBox.getChildren().add(playerLabels[0]);
            lowerBox.getChildren().add(playerLabels[1]);
        } else {
            upperBox.getChildren().addAll(playerLabels[0], playerLabels[1]);
            for (int i = 2; i < playerLabels.length; i++) {
                lowerBox.getChildren().add(playerLabels[i]);
            }
        }
        for (int i = 0; i < settings[0]; i++) {
            playerLabels[i].textProperty().bind(players[i].getMessage());
            playerLabels[i].setStyle("-fx-border-color: #" + colors[i] + ";");
        }
        fxLabels.getChildren().addAll(upperBox, lowerBox);
    }

    /**
     * Handles the settings dialog and updates game settings.
     * @return 0 if settings were updated, 1 if cancelled.
     */
    private int settingsHandler() {
        settingsDialog.open();
        if (settingsDialog.getResult()[0][0] == null) return 1;
        settings = new double[3];
        playerNames = new String[MAX_PLAYERS];
        bots = new boolean[MAX_PLAYERS];
        for (int i = 0; i < MAX_PLAYERS; i++) {
            if (i < 3) {
                settings[i] = Double.parseDouble(settingsDialog.getResult()[0][i]);
            }
            playerNames[i] = settingsDialog.getResult()[1][i];
            bots[i] = Boolean.parseBoolean(settingsDialog.getResult()[2][i]);
        }
        findValidBoardSize((int) settings[1]);
        return 0;
    }

    /**
     * Creates a new game with current settings.
     */
    public void createGame() {
        game = new Game(rows, cols, settings[2]);
        for (int i = 0; i < settings[0]; i++) {
            game.addPlayer(playerNames[i], bots[i], i);
        }
        players = game.getPlayers();
        game.newGame();
        dice = game.getDice();
        playerIcons = new ImageView[MAX_PLAYERS];
        createGrid();
        createDiceListener();
        createPlayers();
        setPlayersStartingPos();

        game.botTurnProperty().addListener((obs, oldVal, newVal) ->
            fxDice.setMouseTransparent(newVal)
        );
        game.setOnBotTurn(() -> Platform.runLater(this::diceHandler));

        visualSnakesLadders();
        createLabels();
        updatePlayerPosition(players[0], playerIcons[0]);
        updateLabels();
        fxGrid.getWidth();
    }

    private void updateLabels() {
        for (int i = 0; i < settings[0]; i++) {
            if (game.getNextPlayer() == players[i]) {
                playerLabels[i].setScaleX(1);
                playerLabels[i].setScaleY(1);
            } else {
                playerLabels[i].setScaleX(0.9);
                playerLabels[i].setScaleY(0.9);
            }
        }

    }

    /**
     * Creates icons that represent the players.
     */
    private void createPlayers() {
        for (int i = 0; i < settings[0]; i++) {
            ImageView currentIcon = new ImageView(icons[i]);
            playerIcons[i] = currentIcon;
            GridPane.setHalignment(currentIcon, HPos.CENTER);
            GridPane.setValignment(currentIcon, VPos.CENTER);
            currentIcon.setFitHeight(SIZE_OF_ICONS);
            currentIcon.setFitWidth(SIZE_OF_ICONS);
            currentIcon.pickOnBoundsProperty();
            currentIcon.preserveRatioProperty();
            fxGrid.getChildren().add(currentIcon);
            playerDisplayListener(players[i], currentIcon);

        }
    }

    /**
     * Finds valid board dimensions.
     * @param size maximum tiles.
     */
    private void findValidBoardSize(int size) {
        rows = (int) Math.round(Math.sqrt(size) - 0.9);
        cols = Math.round((float) size / rows);
        settings[1] = rows * cols;
    }

    /**
     * Sets the starting position of the players.
     */
    private void setPlayersStartingPos() {
        Label label = gridLabels.getFirst();
        int startCol = GridPane.getColumnIndex(label);
        int startRow = GridPane.getRowIndex(label);
        for (int i = 0; i < settings[0]; i++) {
            GridPane.setColumnIndex(playerIcons[i], startCol);
            GridPane.setRowIndex(playerIcons[i], startRow);
        }
    }

    /**
     * Creates the grid for the board based on total rows and columns.
     */
    public void createGrid() {
        fxGrid.getChildren().clear();
        gridLabels.clear();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int col = (i % 2 == 0) ? j : cols - j - 1;
                int row = rows - i - 1;
                int index = i * cols + j + 1;
                gridLabels.add(new Label(index + ""));
                Label label = gridLabels.get(index - 1);
                label.getStyleClass().add("tile"+index%2);
                fxGrid.add(label, col, row);
            }
        }
    }

    /**
     * Creates a listener to dice so each value is represented with the correct image.
     */
    public void createDiceListener() {
        dice.getProp().addListener(event -> fxDice.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/dice/" + dice.getNumber() + ".png")))));
    }

    /**
     * Handles dice rolls and game progression when dice is pressed.
     */
    public void diceHandler() {
        int result = game.round();
        if (result == -1 || finished) {
            newGameHandler();
        } else if (result == 1) {
            String winner = game.getNextPlayer().getName() + " reached the end! \uD83C\uDFC1";
            game.getNextPlayer().setMessage(winner);
            for (int i = 0; i < settings[0]; i++) {
                if (players[i] != game.getNextPlayer()) {
                    playerLabels[i].getStyleClass().add("loser");
                }
            }
            finished = true;
            return;
        }
        updateLabels();
    }

    /**
     * Handles starting a new game.
     * Is only activated when settings are updated.
     */
    public void newGameHandler() {
        if (settingsHandler() != 0) {
            return;
        }
        finished = false;
        createGame();
    }

    /**
     * Sets up a listener for player position changes.
     * @param player The player to track.
     * @param icon The icon of the player.
     */
    public void playerDisplayListener(Player player, ImageView icon) {
        player.getTileProp().addListener((observable, oldValue, newValue) -> {
            Label label = gridLabels.get(newValue.intValue() - 1);
            int col = GridPane.getColumnIndex(label);
            int row = GridPane.getRowIndex(label);
            GridPane.setColumnIndex(icon, col);
            GridPane.setRowIndex(icon, row);
            updatePlayerPosition(player, icon);
        });
    }

    /**
     * Updates the position of player icon on the board.
     * @param player The player to track.
     * @param icon The icon of the player.
     */
    public void updatePlayerPosition(Player player, ImageView icon) {
        int offset = 0;
        for (int j = 0; j < settings[0]; j++) {
            if (player.getTile() == players[j].getTile() && icon != playerIcons[j]) {
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
                iconsOnTile.get(iconsOnTile.size() - j - 1).toFront();
                iconsOnTile.get(j).setTranslateX((double) (-offset * 10) / 2 + 10 * j);
            }
        } else {
            icon.setTranslateX(0);
            icon.toFront();
        }
    }

    /**
     * Adds the icons and size of snakes and ladders.
     */
    public void visualSnakesLadders() {
        HashMap<Integer, Integer> map = game.getConnectionMap();
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

            Label label = gridLabels.get(key - 1);
            int col = GridPane.getColumnIndex(label);
            int row = GridPane.getRowIndex(label);

            ImageView view = new ImageView();
            view.setFitHeight(size);
            view.setFitWidth(size);
            GridPane.setValignment(view, VPos.BOTTOM);
            GridPane.setHalignment(view, HPos.RIGHT);
            view.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/" + img + ".png"))));
            fxGrid.add(view, col, row);
        }
    }
}
