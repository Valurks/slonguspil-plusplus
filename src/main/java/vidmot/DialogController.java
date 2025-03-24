package vidmot;

import javafx.scene.control.TextInputDialog;
import vinnsla.Player;

import java.util.Optional;

public class DialogController {

    private final Player player;

    public DialogController(Player player) {
        this.player = player;
    }

    public void showDialog() {
        TextInputDialog d = new TextInputDialog();
        d.setTitle("Leikmenn");
        d.setHeaderText("Sláðu inn leikmannsnafn:");
        d.setContentText("Nafn:");

        Optional<String> utkoma = d.showAndWait();
        if (utkoma.isPresent() && !utkoma.get().isEmpty()) {
            player.setName(utkoma.get());
        } else {
            player.setName("Ónefndur Leikmaður");
        }

    }
}
