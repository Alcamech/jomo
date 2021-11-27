package com.alcamech.jomo;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, NumberFormatException {
        boolean isContinue;
        Pomodoro pomodoro = new Pomodoro();
        do {
            for(int i = 0; i<pomodoro.getPomNum(); i++) {
                Message.startPomo();
                Countdown.bySleep(pomodoro.getPomodoroTime());
                if(i == pomodoro.getPomNum()-1) {
                    Message.startLongBreak();
                    Countdown.bySleep(pomodoro.getLongBreakTime());
                } else {
                    Message.startShortBreak();
                    Countdown.bySleep(pomodoro.getShortBreakTime());
                }
            }
            isContinue = Message.isContinue();
        } while (isContinue);
    }
}
