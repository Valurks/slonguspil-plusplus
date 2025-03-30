package vidmot;

import javafx.event.ActionEvent;

/**
 * Nafn: Hjörleifur Örn Sveinsson
 * Gmail: hjorleifursveins@gmail.com
 * Lýsing:
 */
public class UpphafController {
    public void onByrja(ActionEvent actionEvent) {
        ViewSwitcher.switchTo(View.GAME);
    }

    public void onLeikmenn(ActionEvent actionEvent) {
        ViewSwitcher.switchTo(View.PLAYER);
    }
}
