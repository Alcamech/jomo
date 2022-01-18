module com.alcamech.jomo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.alcamech.jomo to javafx.fxml;
    exports com.alcamech.jomo;
}