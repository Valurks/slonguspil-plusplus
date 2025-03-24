package vidmot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SlangaApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SlangaApplication.class.getResource("slanga-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 520);
        stage.setResizable(false);
        stage.setTitle("Slönguspil");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
