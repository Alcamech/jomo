package com.alcamech.jomo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class JomoController {
    @FXML
    private Pane mainPane;
    @FXML
    private ImageView play;
    @FXML
    private ImageView skip;
    @FXML
    private ImageView close;
    @FXML
    private Label timer;
    @FXML
    private Label title;

    private double x,y;
    private boolean isRunning = false;
    private boolean isBreak = false;
    private Timer pomTimer = new Timer();
    private final int pomLength = 25;
    private final int shortBreakLength = 5;
    private final int longBreakLength = 15;
    private int counter = 60 * pomLength;
    private int circuitNum = 1;

    Image startIcon = new Image("start.png");
    Image stopIcon = new Image("stop.png");
    //String bells = "src/main/resources/sounds/dreamy-bells.wav";
    AudioClip bells = new AudioClip(getClass().getResource("/sounds/dreamy-bells.wav").toExternalForm());

    public void init(Stage stage) {
        setTimerText();
        mainPane.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });

        mainPane.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX()-x);
            stage.setY(mouseEvent.getScreenY()-y);
        });
    }

    public void playBells() {
        bells.play();
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
        stage.setX(primaryStage.getX()+25);
        stage.setY(primaryStage.getY()+25);
        stage.showAndWait();
    }

    public void closeOptionsWindow(MouseEvent e) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    public void closeApplication(MouseEvent e) {
        Platform.exit();
        System.exit(0);
    }

    public void pomodoro(MouseEvent e) throws IOException {
        if(isRunning) {
            pauseTimer();
            isRunning = false;
            play.setImage(startIcon);
        } else {
            startTimer();
            isRunning = true;
            playBells();
            play.setImage(stopIcon);
        }
    }

    public void skipTimer() {
        isRunning = false;
        pauseTimer();
        if(circuitNum != 4 && !isBreak) {
            title.setText("Short Break");
            isBreak = true;
            counter = shortBreakLength * 60;
            setTimerText();
        } else if (circuitNum == 4 && !isBreak) {
            title.setText("Long Break");
            isBreak = true;
            counter = longBreakLength * 60;
            setTimerText();
        } else if (circuitNum == 4) {
            isBreak = false;
            counter = pomLength * 60;
            circuitNum = 1;
            setTimerText();
        } else {
            isBreak = false;
            counter = pomLength * 60;
            circuitNum++;
            setTimerText();
        }

        if(!isBreak) {
            title.setText("Pomodoro " + circuitNum);
        }
    }

    public void pauseTimer() {
        pomTimer.cancel();
        play.setImage(startIcon);
    }

    public void setTimerText() {
        int seconds = counter % 60;
        int minutes = counter / 60;
        if (seconds < 10 && minutes < 10) {
            timer.setText("0" + minutes + ":0" + seconds);
        } else if (minutes < 10) {
            timer.setText("0" + minutes + ":" + seconds);
        } else if (seconds < 10) {
            timer.setText(minutes + ":0" + seconds);
        }
        else {
            timer.setText(minutes + ":" + seconds);
        }
    }

    public void startTimer() {
        pomTimer = new Timer();
        pomTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(!isRunning) {
                        pauseTimer();
                    } else if(!isBreak) {
                        title.setText("Pomodoro " + circuitNum);
                    }

                    setTimerText();
                    counter--;

                    if (counter == 0 && circuitNum == 4 && !isBreak) { // long break
                        playBells();
                        title.setText("Long Break");
                        isBreak = true;
                        isRunning = false;
                        counter = longBreakLength * 60;
                        circuitNum = 0;
                    } else if (counter == 0 && !isBreak) { // short break
                        playBells();
                        title.setText("Short Break");
                        counter = shortBreakLength * 60;
                        isBreak = true;
                        isRunning = false;
                    } else if (counter == 0) { // break finished
                        playBells();
                        counter = pomLength * 60;
                        circuitNum++;
                        isBreak = false;
                        isRunning = false;
                    }
                });
            }
        }, 0, 1000);
    }

}