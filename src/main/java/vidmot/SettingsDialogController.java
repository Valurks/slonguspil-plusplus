package vidmot;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.HashMap;

/**
 * A dialog controller that handles and manages the settings of the game.
 */
public class SettingsDialogController {

    private Dialog<String[][]> dialog;
    private VBox mainView;
    private String[] playerNames;
    private String[] originalplayerNames;
    private Boolean[] bots;
    private Boolean[] originalBots;
    private double[] settings;
    private double[] originalSettings;

    // settings[0]: number of players
    // settings[1]: board size
    // settings[2]: difficulty

    /**
     * Constructs a new instance of the SettingsDialogController
     */
    public SettingsDialogController() {
        playerNames = new String[4];
        Arrays.fill(playerNames, "Unnamed player");
        bots = new Boolean[4];
        Arrays.fill(bots, false);
        settings = new double[]{2, 24, 1};
        save();
    }

    /**
     * Sets the dialog up
     */
    private void initializeDialog() {
        load();
        mainView = new VBox();
        HBox mainViewContainer = new HBox();
        dialog = new Dialog<>();
        DialogPane dialogPane = new DialogPane();
        dialogPane.setMinHeight(600);
        dialogPane.setMinWidth(600);
        mainViewContainer.setMaxWidth(350);
        mainViewContainer.setAlignment(Pos.CENTER);
        mainView.setSpacing(20);
        mainView.setPadding(new Insets(90, 0, 0, 0));
        mainView.getChildren().add(new Region());

        createPlayerSlider();
        createBoardSizeSelector();
        createDifficultySelector();
        createButtons();

        mainViewContainer.getChildren().add(mainView);
        dialogPane.setContent(mainViewContainer);
        dialogPane.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());
        dialogPane.getStyleClass().add("backgroundPaneDialog");
        dialog.setDialogPane(dialogPane);
    }

    /**
     * Creates the slider used in the dialog.
     * The slider decides how many players are playing.
     */
    private void createPlayerSlider() {
        VBox playerBox = new VBox();
        Label playersLabel = new Label("Number of players");
        Slider playerSlider = new Slider(2, 4, settings[0]);

        playerSlider.setShowTickMarks(true);
        playerSlider.setMajorTickUnit(1f);
        playerSlider.setShowTickLabels(true);
        playerSlider.setMinorTickCount(0);

        playerSlider.valueProperty().addListener((obs, oldval, newVal) -> {
            int value = (int) Math.round(newVal.doubleValue());
            playerSlider.setValue(value);
            settings[0] = value;
            playerBox.getChildren().remove(2);
            playerBox.getChildren().add(createPlayersInput());
        });

        playerBox.getChildren().addAll(playersLabel, playerSlider, createPlayersInput());
        mainView.getChildren().add(playerBox);
    }

    /**
     * Creates the boardSlider.
     * The slider decides how big the board is.
     */
    private void createBoardSizeSelector() {
        VBox boardBox = new VBox();
        Label boardLabel = new Label("Board Size");
        Slider boardSlider = new Slider(0, 100, settings[1]);
        boardSlider.setShowTickMarks(true);
        boardSlider.setMajorTickUnit(25f);
        boardSlider.setShowTickLabels(true);
        boardSlider.setMinorTickCount(0);
        boardSlider.valueProperty().addListener((obs, oldval, newVal) -> {
            int value = (int) Math.max(Math.round(newVal.doubleValue()),15);
            boardSlider.setValue(value);
            settings[1] = value;
        });

        boardBox.getChildren().addAll(boardLabel, boardSlider);
        mainView.getChildren().add(boardBox);
    }

    /**
     * Creates the difficulty radioboxes.
     * The radioboxes decide what difficulty the game will be.
     */
    private void createDifficultySelector() {
        HBox difficultyBox = new HBox();
        HashMap<String, Double> difficultyMap = new HashMap<>();
        ToggleGroup group = new ToggleGroup();
        String[] labels = new String[]{"Easy","Normal","Hard"};
        difficultyMap.put(labels[0], 0.75);
        difficultyMap.put(labels[1], 1.0);
        difficultyMap.put(labels[2], 1.5);
        VBox[] radioBoxes = new VBox[3];
        for (int i = 0; i < labels.length; i++) {
            radioBoxes[i] = createRadioButton(labels[i], group);
        }
        HashMap<Double, Integer> difficultyToIndexTranslator = new HashMap<>();
        difficultyToIndexTranslator.put(0.75, 0);
        difficultyToIndexTranslator.put(1.0, 1);
        difficultyToIndexTranslator.put(1.5, 2);

        int currentDifficultyIndex = difficultyToIndexTranslator.get(originalSettings[2]);
        RadioButton currentRadio = (RadioButton) radioBoxes[currentDifficultyIndex].getChildren().get(1);
        group.selectToggle(currentRadio);

        difficultyBox.getChildren().addAll(radioBoxes[0], radioBoxes[1], radioBoxes[2]);
        group.selectedToggleProperty().addListener((obs, oldval, newval) -> {
            String value = ((RadioButton) newval).getId();
            settings[2] = difficultyMap.get(value);
        });
        difficultyBox.setSpacing(10);
        mainView.getChildren().add(difficultyBox);
    }

    /**
     * Helper method to create the radio boxes.
     *
     * @param label Difficulty for the label
     * @param group Togglegroup
     * @return A vbox containing the label and radioButton
     */
    private VBox createRadioButton(String label, ToggleGroup group) {
        VBox radioBox = new VBox();
        RadioButton radio = new RadioButton();
        radio.setToggleGroup(group);
        radio.setId(label);
        radioBox.getChildren().addAll(new Label(label), radio);
        return radioBox;
    }

    /**
     * Creates the buttons in the dialog.
     */
    private void createButtons() {
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);
        Button confirmButton = new Button("Continue");
        Button closeButton = new Button("Cancel");
        confirmButton.setOnAction(event -> {
            save();
            createResult();
        });
        closeButton.setOnAction(event -> dialog.setResult(new String[1][1]));

        buttonBox.getChildren().addAll(closeButton, confirmButton);
        mainView.getChildren().add(buttonBox);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
    }

    /**
     * Creates the HBoxes that contain the player name textboxes.
     *
     * @param index of player.
     * @return the HBox for the current player.
     */
    private HBox createPlayerHBox(int index) {
        HBox mainBox = new HBox();
        mainBox.setSpacing(5);

        TextField playerName = createPlayerTextField(index);
        CheckBox bot = createPlayerCheckBox(index);

        mainBox.getChildren().addAll(playerName, bot);
        return mainBox;
    }

    /**
     * Creates a checkbox to control whether a specific player is a bot or not,
     * player is based on the player index
     *
     * @param index of the player
     * @return CheckBox to control a player's bot attribute
     */
    private CheckBox createPlayerCheckBox(int index) {
        CheckBox bot = new CheckBox("bot");
        bot.setId(index + "");
        bot.setSelected(bots[index]);
        bot.selectedProperty().addListener((obs, oldval, newval) -> bots[index] = newval);
        if (index == 0) {
            bot.setVisible(false);
        }
        return bot;
    }

    /**
     * Creates a textfield to edit a specific player's name,
     * player is based on the player index
     *
     * @param index of player.
     * @return TextField to edit player's name
     */
    private TextField createPlayerTextField(int index) {
        TextField playerName = new TextField();
        String defaultName = "Unnamed player";
        if (playerNames[index].equals(defaultName)) {
            playerName.setPromptText(defaultName);
        } else {
            playerName.setText(playerNames[index]);
        }
        playerName.setOnKeyTyped(event -> {
            if (playerName.getText().trim().isBlank()) {
                playerName.setPromptText(defaultName);
            } else {
                playerNames[index] = playerName.getText().trim();
            }
        });
        return playerName;
    }

    /**
     * Creates a VBox for the player input.
     *
     * @return the VBox.
     */
    private VBox createPlayersInput() {
        VBox playersBox = new VBox();
        playersBox.setSpacing(5);
        for (int i = 0; i < settings[0]; i++) {
            playersBox.getChildren().add(createPlayerHBox(i));
        }
        return playersBox;
    }

    /**
     * Creates the result of the dialog.
     */
    private void createResult() {
        String[][] result = new String[3][4];
        for (int i = 0; i < 4; i++) {
            if (i < 3) result[0][i] = originalSettings[i] + "";
            result[2][i] = bots[i] + "";
        }
        result[1] = playerNames.clone();
        dialog.setResult(result);
    }

    /**
     * Saves the current changes.
     */
    private void save() {
        originalSettings = settings.clone();
        originalplayerNames = playerNames.clone();
        originalBots = bots.clone();
    }

    /**
     * Loads the previous settings.
     */
    private void load() {
        settings = originalSettings.clone();
        playerNames = originalplayerNames.clone();
        bots = originalBots.clone();
    }

    /**
     * Opens and shows the dialog.
     */
    public void open() {
        initializeDialog();
        dialog.showAndWait();
    }

    /**
     * Returns the result of the dialog.
     * @return a 2D array of Strings representing the result.
     */
    public String[][] getResult() {
        return dialog.getResult();
    }
}
