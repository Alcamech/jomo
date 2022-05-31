package com.alcamech.jomo;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class JomoController {
    @FXML
    private Pane mainPane;
    @FXML
    private ImageView play, skip, close;
    @FXML
    private Label timer, title;
    @FXML
    private TextField pomLengthInput, shortBreakLengthInput, longBreakLengthInput, sessionsUntilBreakInput;

    private double x,y;
    private boolean isRunning, isBreak = false;
    private Timer pomTimer = new Timer();
    private final int pomLength = 25;
    private final int shortBreakLength = 5;
    private final int longBreakLength = 15;
    private final int sessionUntilBreak = 4;
    private int counter = 60 * pomLength;
    private int circuitNum = 1;

    Image startIcon = new Image("start.png");
    Image stopIcon = new Image("stop.png");
    AudioClip bells = new AudioClip(Objects.requireNonNull(getClass().getResource("/sounds/dreamy-bells.wav")).toExternalForm());

    public void init(Stage stage) {
        updateTimerText();
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
        bells.setVolume(0.3);
        bells.play();
    }

    public void switchToJomoOptions(MouseEvent e) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("jomoOptions.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.WINDOW_MODAL);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        Stage primaryStage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.initOwner(primaryStage);
        stage.setX(primaryStage.getX());
        stage.setY(primaryStage.getY());
        stage.showAndWait();
    }

    public void closeOptionsWindow(MouseEvent e) {
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }

    public void closeApplication() {
        Platform.exit();
        System.exit(0);
    }

    public void pomodoro() {
        if(isRunning) {
            pauseTimer();
            isRunning = false;
        } else {
            startTimer();
            isRunning = true;
            playBells();
        }
        updateUI();
    }

    public void skipTimer() {
        isRunning = false;
        pauseTimer();
        if(circuitNum != sessionUntilBreak && !isBreak) {
            isBreak = true;
            counter = shortBreakLength * 60;
        } else if (circuitNum == sessionUntilBreak && !isBreak) {
            isBreak = true;
            counter = longBreakLength * 60;
        } else if (circuitNum == sessionUntilBreak) {
            isBreak = false;
            counter = pomLength * 60;
            circuitNum = 1;
        } else {
            isBreak = false;
            counter = pomLength * 60;
            circuitNum++;
        }
        updateUI();
    }

    public void pauseTimer() {
        pomTimer.cancel();
        play.setImage(startIcon);
    }

    public void updateUI() {
        updateTitle();
        updateTimerText();
        updatePlayIcon();
    }

    public void updateTimerText() {
        int seconds = counter % 60;
        int minutes = counter / 60;
        timer.setText(String.format("%02d:%02d", minutes, seconds));
    }

    public void updateTitle() {
        if(isBreak) {
            if(circuitNum == 4) {
                title.setText("Long Break");
            } else {
                title.setText("Short Break");
            }
        } else {
            title.setText("Pomodoro " + circuitNum);
        }
    }

    public void updatePlayIcon() {
        if(isRunning) {
            play.setImage(stopIcon);
        } else {
            play.setImage(startIcon);
        }
    }

    public void startTimer() {
        pomTimer = new Timer();
        pomTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(isRunning) {
                        counter--;
                        if(counter == 0) {
                            playBells();
                            isRunning = false;
                            if(circuitNum == sessionUntilBreak && !isBreak) {
                                isBreak = true;
                                counter = longBreakLength * 60;
                                circuitNum = 0;
                            } else if (!isBreak) {
                                counter = shortBreakLength * 60;
                                isBreak = true;
                            } else {
                                counter = pomLength * 60;
                                circuitNum++;
                                isBreak = false;
                            }
                        }
                    } else {
                        pauseTimer();
                    }
                    updateUI();
                });
            }
        }, 0, 1000);
    }
}