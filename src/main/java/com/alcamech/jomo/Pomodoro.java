package com.alcamech.jomo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Pomodoro {
    private int pomodoroTime;
    private int shortBreakTime;
    private int pomNum;
    private int longBreakTime;

    public Pomodoro() throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Please Enter:");
        System.out.println("Pomodoro time (in minutes): ");
        pomodoroTime = Integer.parseInt(br.readLine());
        System.out.println("Short break time (in minutes): ");
        shortBreakTime = Integer.parseInt(br.readLine());
        System.out.println("Long break time (in minutes): ");
        longBreakTime = Integer.parseInt(br.readLine());
        System.out.println("Enter the number of pomodoros: ");
        pomNum = Integer.parseInt(br.readLine());
    }

    public int getPomodoroTime() {
        return pomodoroTime;
    }

    public int getShortBreakTime() {
        return shortBreakTime;
    }

    public int getPomNum() {
        return pomNum;
    }

    public int getLongBreakTime() {
        return longBreakTime;
    }
}
