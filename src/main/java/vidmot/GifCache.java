package vidmot;

import javafx.scene.image.Image;

import java.util.Objects;

/**
 * A class that handles loading and managing the GIF´s
 */
public class GifCache {

    private static final Image[] icons = new Image[4];

    /**
     * Initializes the GIF´s
     */
    public static void initialize() {
        for (int i = 0; i < 4; i++) {
            icons[i] = new Image(
                    Objects.requireNonNull(GifCache.class.getResourceAsStream(
                            "/vidmot/images/characters/" + i + ".gif"
                    ))
            );
        }
        System.out.println("Loaded " + icons.length + " gif images");
    }

    /**
     * Returns the GIF´S
     * @return icons
     */
    public static Image[] getIcons() {
        return icons;
    }
}
