module com.alcamech.jomo {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.alcamech.jomo to javafx.fxml;
    exports com.alcamech.jomo;
}