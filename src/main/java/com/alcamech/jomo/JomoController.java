package com.alcamech.jomo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class JomoController {
    @FXML
    private Pane mainPane;

    private double x,y;

    public void init(Stage stage) {
        mainPane.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });

        mainPane.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX()-x);
            stage.setY(mouseEvent.getScreenY()-y);
        });
    }

    public void switchScenes(MouseEvent e, String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Jomo.class.getResource(fxmlFile));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        ((JomoController)fxmlLoader.getController()).init(stage);
        stage.show();
    }

    public void swtichToJomo(MouseEvent e) throws IOException {
        switchScenes(e, "jomo.fxml");
    }

    public void switchToJomoOptions(MouseEvent e) throws IOException{
        switchScenes(e, "jomoOptions.fxml");
    }
}