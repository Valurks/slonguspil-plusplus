package vidmot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main Application for SlönguSpil++.
 * Responsible for starting the game.
 *
 * @author Hjörleifur Örn Sveinsson, hos40@hi.is
 * @author Valur Kristinn Starkaðarson, vks5@hi.is
 * @since 14.03.25
 * @version 1.0
 */
public class SlangaApplication extends Application {
    /**
     * Starts the main application.
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException if scene does not exist.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SlangaApplication.class.getResource("upphaf-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 850);
        ViewSwitcher.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Slönguspil++");
        stage.setScene(scene);
        stage.show();
        GifLoader.initialize();
    }

    /**
     * Main method that launches the application
     * @param args not used.
     */
    public static void main(String[] args) {
        launch();
    }
}
