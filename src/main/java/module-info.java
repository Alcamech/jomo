module com.alcamech.jomo {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;


    opens com.alcamech.jomo to javafx.fxml;
    exports com.alcamech.jomo;
}