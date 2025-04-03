package vidmot;

import javafx.scene.image.Image;

import java.util.Objects;

public class GifLoader {

    private static final Image[] icons = new Image[4];

    public static void init() {
        for (int i = 0; i < 4; i++) {
            icons[i] = new Image(
                    Objects.requireNonNull(GifLoader.class.getResourceAsStream(
                            "/vidmot/images/characters/" + i + ".gif"
                    ))
            );
        }
        System.out.println("Loaded " + icons.length + " gif images");
    }

    public static Image[] getIcons() {
        return icons;
    }
}
