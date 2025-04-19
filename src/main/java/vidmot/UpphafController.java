package vidmot;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;

import java.util.Objects;

/**
 * Controller class of the starting screen.
 */
public class UpphafController {

    public ImageView fxSnake1;
    public ImageView fxSnake2;
    public Button fxStart;

    /**
     * Initializes the controller class by setting animation and start button.
     */
    public void initialize() {
        animatesnakes();
        setStartButton();
    }

    /**
     * Sets the start button.
     */
    private void setStartButton() {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/vidmot/images/backgrounds/StartButton.png")));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        fxStart.setGraphic(imageView);
        fxStart.setOnMouseEntered(e -> {
            fxStart.setScaleX(1.1);
            fxStart.setScaleY(1.1);
        });

        fxStart.setOnMouseExited(e -> {
            fxStart.setScaleX(1.0);
            fxStart.setScaleY(1.0);
        });
    }

    /**
     * Creates 2 animated snakes.
     */
    private void animatesnakes() {
        Path path = new Path();
        path.getElements().add(new MoveTo(-200, 250));
        fxSnake1.setScaleX(-1);

        path.getElements().add(new QuadCurveTo(150, 300, 250, 200));
        path.getElements().add(new QuadCurveTo(350, 100, 450, 200));
        path.getElements().add(new QuadCurveTo(550, 300, 650, 200));
        path.getElements().add(new QuadCurveTo(750, 100, 850, 200));
        path.getElements().add(new QuadCurveTo(950, 300, 950, 200));
        path.getElements().add(new QuadCurveTo(1150, 100, 1050, 200));
        PathTransition pathTransition1 = new PathTransition();
        setPath(fxSnake1,pathTransition1,path);

        Path path2 = new Path();
        fxSnake2.setScaleX(-1);
        path2.getElements().add(new MoveTo(800, 600));


        path2.getElements().add(new QuadCurveTo(700, 450, 600, 400));
        path2.getElements().add(new QuadCurveTo(500, 300, 400, 400));
        path2.getElements().add(new QuadCurveTo(300, 500, 200, 400));
        path2.getElements().add(new QuadCurveTo(100, 300, 0, 400));
        path2.getElements().add(new QuadCurveTo(-100, 550, -200, 400));
        path2.getElements().add(new QuadCurveTo(-300, 300, -400, 400));

        path2.setStroke(Color.DARKSEAGREEN);
        path2.setStrokeWidth(12);
        path2.setFill(null);
        PathTransition pathTransition2 = new PathTransition();
        setPath(fxSnake2,pathTransition2,path2);


        pathTransition1.play();
        pathTransition2.play();
    }

    /**
     * Sets the path of the snake.
     * @param snake Current snake.
     * @param pathTransition The pathTransition of current snake.
     * @param path The path of current snake.
     */
    private void setPath(ImageView snake, PathTransition pathTransition, Path path){
        pathTransition.setNode(snake);
        pathTransition.setDuration(Duration.seconds(11));
        pathTransition.setPath(path);
        pathTransition.setCycleCount(PathTransition.INDEFINITE);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setInterpolator(Interpolator.LINEAR);
    }

    /**
     * Handles when start button is pressed.
     */
    public void onStart() {
        ViewSwitcher.switchTo(View.GAME);
    }
}
