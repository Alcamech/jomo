package com.alcamech.jomo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class JomoController {
    @FXML
    private Pane mainPane;
    @FXML
    private Button play;
    @FXML
    private Button skip;
    @FXML
    private Button close;
    @FXML
    private Label timer;
    @FXML
    private Label circuit;

    private double x,y;
    private boolean isRunning = false;
    private boolean isBreak = false;
    private Timer pomTimer = new Timer();
    private int counter = 60 * 25;
    private int seconds = 0;
    private int minutes = 0;
    private int circuitNum = 1;

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

    public void switchToJomoOptions(MouseEvent e) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Jomo.class.getResource("jomoOptions.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        Stage primaryStage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.initOwner(primaryStage);
        stage.setX(primaryStage.getX()+50);
        stage.setY(primaryStage.getY()+50);
        stage.showAndWait();
    }

    public void closeOptionsWindow(MouseEvent e) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }


    public void pomodoro(MouseEvent e) throws IOException {
        if(isRunning) {
            pauseTimer();
            isRunning = false;
            play.setText("Start");
        } else {
            startTimer();
            isRunning = true;
            play.setText("Stop");
        }
    }

    public void pauseTimer() {
        pomTimer.cancel();
    }

    public void startTimer() {
        pomTimer = new Timer();
        pomTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    counter--;

                    seconds = counter % 60;
                    minutes = counter / 60;
                    if (seconds < 10 && minutes < 10) {
                        timer.setText("0" + minutes + ":0" + seconds);
                    } else if (minutes < 10 && seconds > 10) {
                        timer.setText("0" + minutes + ":" + seconds);
                    } else if (seconds < 10 && minutes > 10) {
                        timer.setText(minutes + ":0" +seconds);
                    }
                    else {
                        timer.setText(minutes + ":" + seconds);
                    }

                    if (counter == 0 && circuitNum == 4 && !isBreak) { // long break
                        isBreak = true;
                        counter = 15 * 60; // 15 * 60 Testing: 5
                        circuitNum = 0;
                    } else if (counter == 0 && !isBreak) { // short break
                        counter = 5 * 60; // 5 * 60 Testing: 2
                        isBreak = true;
                    } else if (counter == 0) { // break finished
                        counter = 25 * 60; // 25 * 60 Testing: 10
                        circuitNum++;
                        isBreak = false;
                    }
                });
            }
        }, 0, 1000);
    }

}