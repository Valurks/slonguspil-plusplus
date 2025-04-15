module vidmot.slanga {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens vidmot to javafx.fxml;
    exports vidmot;
    exports vinnsla;
}
