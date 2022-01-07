module com.alcamech.jomo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;

    opens com.alcamech.jomo to javafx.fxml;
    exports com.alcamech.jomo;
}