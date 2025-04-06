package vidmot;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.scene.image.ImageView;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;

/**
 * Nafn: Hjörleifur Örn Sveinsson
 * Gmail: hjorleifursveins@gmail.com
 * Lýsing:
 */
public class UpphafController {
    public ImageView fxSnake1;
    public ImageView fxSnake2;

    public void initialize(){
        animatesnakes();
    }

    private void animatesnakes() {
        Path path = new Path();
        path.getElements().add(new MoveTo(-200, 250));
        fxSnake1.setScaleX(-1);
        //Curves ma laga
        path.getElements().add(new QuadCurveTo(50, 300, 100, 250));
        path.getElements().add(new QuadCurveTo(150, 320, 200, 270));
        path.getElements().add(new QuadCurveTo(250, 320, 300, 270));
        path.getElements().add(new QuadCurveTo(350, 240, 400, 200));
        path.getElements().add(new QuadCurveTo(450, 150, 500, 100));
        path.getElements().add(new QuadCurveTo(550, 50, 600, 50));
        path.getElements().add(new QuadCurveTo(650, 0, 700, 50));
        path.getElements().add(new QuadCurveTo(750, 100, 800, 150));
        path.getElements().add(new QuadCurveTo(850, 200, 900, 150));
        path.getElements().add(new QuadCurveTo(950, 100, 1000, 50));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setNode(fxSnake1);
        pathTransition.setDuration(Duration.seconds(9));
        pathTransition.setPath(path);
        pathTransition.setCycleCount(PathTransition.INDEFINITE);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setInterpolator(Interpolator.LINEAR);
        pathTransition.play();

        //snakur 2
        Path path2 = new Path();
        fxSnake2.setScaleX(-1);
        path2.getElements().add(new MoveTo(800, 0));
        path2.getElements().add(new QuadCurveTo(750, 100, 700, 150));
        path2.getElements().add(new QuadCurveTo(650, 200, 600, 150));
        path2.getElements().add(new QuadCurveTo(550, 250, 500, 200));
        path2.getElements().add(new QuadCurveTo(450, 300, 400, 250));
        path2.getElements().add(new QuadCurveTo(350, 350, 300, 300));
        path2.getElements().add(new QuadCurveTo(250, 400, 200, 350));
        path2.getElements().add(new QuadCurveTo(150, 450, 100, 400));
        path2.getElements().add(new QuadCurveTo(50, 500, 0, 450));
        path2.getElements().add(new QuadCurveTo(-100, 600, -150, 650));

        PathTransition pathTransition2 = new PathTransition();
        pathTransition2.setNode(fxSnake2);
        pathTransition2.setDuration(Duration.seconds(8));
        pathTransition2.setPath(path2);
        pathTransition2.setCycleCount(PathTransition.INDEFINITE);
        pathTransition2.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setInterpolator(Interpolator.LINEAR);
        pathTransition2.play();
    }


    public void onByrja() {
        ViewSwitcher.switchTo(View.GAME);
    }
}
