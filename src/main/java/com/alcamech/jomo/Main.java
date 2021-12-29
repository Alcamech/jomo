package com.alcamech.jomo;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, NumberFormatException {
        boolean isContinue;
        Pomodoro pomodoro = new Pomodoro();
        do {
            Message.showCircuitNumber(pomodoro.getCircuit());
            for(int i = 0; i<pomodoro.getPomNum(); i++) {
                Message.startPomo();
                Countdown.bySleep(pomodoro.getPomodoroTime());
                if(i == pomodoro.getPomNum()-1) {
                    isContinue = Message.isContinue("Ready to start a long break?");
                    if(!isContinue) { continue; }
                    Message.startLongBreak();
                    Countdown.bySleep(pomodoro.getLongBreakTime());
                } else {
                    isContinue = Message.isContinue("Ready to start a short break?");
                    if(!isContinue) { continue; }
                    Message.startShortBreak();
                    Countdown.bySleep(pomodoro.getShortBreakTime());
                    isContinue = Message.isContinue("Ready to keep working?");
                    if(!isContinue) { break; }
                }
            }
            pomodoro.increaseCircuit();
            isContinue = Message.isContinue("Good job! You've completed a circuit. Want to go again?");
        } while (isContinue);
    }
}
