package vidmot;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.HashMap;

public class SettingsDialogController {

    private Dialog<String[][]> dialog;
    private VBox mainView;
    private String[] playerNames;
    private String[] originalplayerNames;
    private Boolean[] bots;
    private Boolean[] originalBots;
    private double[] settings;
    private double[] originalSettings;
    /*
    * settings[0] er fjöldi leikmanna
    * settings[1] er stærð borðs
    * settings[2] er erfiðleikastig
    * */

    public SettingsDialogController() {
        playerNames = new String[4];
        Arrays.fill(playerNames, "Ónefndur leikmaður");
        bots = new Boolean[4];
        Arrays.fill(bots, false);
        settings = new double[]{2,24,1};
        save();
    }

    private void initializeDialog() {
        load();
        mainView = new VBox();
        dialog = new Dialog<>();
        DialogPane dialogPane = new DialogPane();
        dialogPane.setMinHeight(400);
        dialogPane.setMinWidth(400);
        mainView.setSpacing(20);
        mainView.getChildren().add(new Region());

        createPlayerSlider();
        createBoardSizeSelector();
        createDifficultySelector();
        createButtons();

        dialogPane.setContent(mainView);
        dialogPane.getStylesheets().add(
                getClass().getResource("css/styles.css").toExternalForm());
        dialogPane.getStyleClass().add("root");
        dialog.setDialogPane(dialogPane);
    }


    private void createPlayerSlider(){
        VBox playerBox = new VBox();
        Label playersLabel = new Label("Number of players");
        Slider playerSlider = new Slider(2, 4, settings[0]);
        playerSlider.setShowTickMarks(true);
        playerSlider.setMajorTickUnit(1f);
        playerSlider.setShowTickLabels(true);
        playerSlider.setMinorTickCount(0);
        playerSlider.valueProperty().addListener(
                (obs, oldval, newVal) ->
                {
                    int value = (int) Math.round(newVal.doubleValue());
                    playerSlider.setValue(value);
                    settings[0] = value;
                    playerBox.getChildren().remove(2);
                    playerBox.getChildren().add(createPlayersInput());
                }
        );
        playerBox.getChildren().addAll(playersLabel, playerSlider, createPlayersInput());
        mainView.getChildren().add(playerBox);
    }


    private void createBoardSizeSelector(){
        VBox boardBox = new VBox();
        Label boardLabel = new Label("Board Size");
        Slider boardSlider = new Slider(15, 100, settings[1]);
        boardSlider.setShowTickMarks(true);
        boardSlider.setMajorTickUnit(25f);
        boardSlider.setShowTickLabels(true);
        boardSlider.setMinorTickCount(0);
        boardSlider.valueProperty().addListener(
                (obs, oldval, newVal) ->
                {
                    int value = (int) Math.round(newVal.doubleValue());
                    boardSlider.setValue(value);
                    settings[1] = value;
                }
        );

        boardBox.getChildren().addAll(boardLabel, boardSlider);
        mainView.getChildren().add(boardBox);
    }

    private void createDifficultySelector(){
        HBox difficultyBox = new HBox();
        HashMap<String, Double> difficultyMap = new HashMap<>();
        ToggleGroup group = new ToggleGroup();
        difficultyMap.put("Easy", 0.75);
        difficultyMap.put("Normal", 1.0);
        difficultyMap.put("Hard", 1.5);
        VBox[] radioBoxes = new VBox[]{
                createRadioButton("Easy",group),
                createRadioButton("Normal",group),
                createRadioButton("Hard",group)
        };
        HashMap<Double,Integer> translator = new HashMap<>();
        translator.put(0.75, 0);
        translator.put(1.0, 1);
        translator.put(1.5, 2);

        group.selectToggle( (RadioButton) radioBoxes[translator.get(originalSettings[2])].getChildren().get(1));

        difficultyBox.getChildren().addAll(radioBoxes[0], radioBoxes[1], radioBoxes[2]);
        group.selectedToggleProperty().addListener(
                (obs, oldval, newval) -> {
                    String value = ((RadioButton)newval).getId();
                    settings[2] = difficultyMap.get(value);
                });
        difficultyBox.setSpacing(10);
        mainView.getChildren().add(difficultyBox);
    }

    private VBox createRadioButton(String label, ToggleGroup group) {
        VBox radioBox = new VBox();
        RadioButton radio = new RadioButton();
        radio.setToggleGroup(group);
        radio.setId(label);
        radioBox.getChildren().addAll(new Label(label), radio);
        return radioBox;
    }

    private void createButtons(){
        HBox buttonBox = new HBox();
        Button confirmButton = new Button("Halda áfram");
        Button closeButton = new Button("Hætta við");
        confirmButton.setOnAction(event -> {
            save();
            createResult();
        });
        closeButton.setOnAction(event -> dialog.setResult(new String[1][1]));

        buttonBox.getChildren().addAll(closeButton, confirmButton);
        mainView.getChildren().add(buttonBox);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
    }

    private HBox createPlayerHBox(int id){
        HBox mainBox = new HBox();
        mainBox.setSpacing(5);
        TextField playerName = new TextField();
        String defaultName = "Ónefndur leikmaður";
        if (playerNames[id].equals(defaultName)) {
            playerName.setPromptText(defaultName);
        } else {
            playerName.setText(playerNames[id]);
        }
        playerName.setOnKeyTyped(event -> {
           if ( playerName.getText().trim().isBlank()) {
               playerName.setPromptText(defaultName);
           } else {
              playerNames[id] = playerName.getText().trim();
           }
        });


        CheckBox bot = new CheckBox("bot");
        bot.setId(id+"");
        bot.setSelected(bots[id]);
        bot.selectedProperty().addListener((obs, oldval, newval) -> bots[id] = newval);
        if (id==0) {
            bot.setDisable(true);
        }

        mainBox.getChildren().addAll(playerName,bot);
        return mainBox;
    }

    private VBox createPlayersInput() {
        VBox playersBox = new VBox();
        playersBox.setSpacing(5);
        for (int i = 0; i < settings[0]; i++ ) {
            playersBox.getChildren().add(createPlayerHBox(i));
        }
        return playersBox;
    }



    private void createResult() {
        String[][] result = new String[3][4];
        for (int i = 0; i < 4; i++) {
            if(i < 3)
                result[0][i] = originalSettings[i] + "";
            result[2][i] = bots[i] + "";

        }
        result[1] = playerNames.clone();
        dialog.setResult(result);
    }

    private void save() {
        originalSettings = settings.clone();
        originalplayerNames = playerNames.clone();
        originalBots = bots.clone();
    }

    private void load() {
        settings = originalSettings.clone();
        playerNames = originalplayerNames.clone();
        bots = originalBots.clone();
    }

    public void open() {
        initializeDialog();
        dialog.showAndWait();
    }

    public String[][] getResult() {
        return dialog.getResult();
    }
}
