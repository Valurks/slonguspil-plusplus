package vidmot;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that handles the transition between views.
 */
public class ViewSwitcher {

    private static final Map<View, Parent> cache = new HashMap<>();

    private static Scene scene;

    /**
     * Sets the current scene.
     *
     * @param scene senan
     */
    public static void setScene(Scene scene) {
        ViewSwitcher.scene = scene;
    }

    /**
     * Switch to scene that is defined in View.
     *
     * @param view The scene being switched to.
     */
    public static void switchTo(View view) {
        if (scene == null) {
            System.out.println("No scene was set");
            return;
        }
        try {
            Parent root;
            if (cache.containsKey(view)) {
                System.out.println("Loading from cache");
                root = cache.get(view);
            } else {
                System.out.println("Loading from FXML");
                root = FXMLLoader.load(ViewSwitcher.class.getResource(view.getFileName()));
                cache.put(view, root);
            }
            scene.setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
