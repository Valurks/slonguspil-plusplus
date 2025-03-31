package vidmot;

import java.util.Arrays;

/**
 * Nafn: Hjörleifur Örn Sveinsson
 * Gmail: hjorleifursveins@gmail.com
 * Lýsing:
 */
public class UpphafController {

    private final SettingsDialogController settings = new SettingsDialogController();
    public void onByrja() {
        ViewSwitcher.switchTo(View.GAME);
    }

    public void onLeikmenn() {
        ViewSwitcher.switchTo(View.PLAYER);
    }

    public void onSettings() {
        settings.onOpen();
        for (String[] val : settings.getResult()) {
            System.out.println(Arrays.toString(val));
        }
    }
}
