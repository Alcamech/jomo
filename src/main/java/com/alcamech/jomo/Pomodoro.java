package com.alcamech.jomo;

public class Pomodoro {
    private int durationInMinutes;
    private Break workBreak;
    private int pomodoroNumber;

    public Pomodoro(int durationInMinutes, Break workBreak, int pomodoroNumber) {
        this.durationInMinutes = durationInMinutes;
        this.workBreak = workBreak;
        this.pomodoroNumber = pomodoroNumber;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public Break getWorkBreak() {
        return workBreak;
    }

    public void setWorkBreak(Break workBreak) {
        this.workBreak = workBreak;
    }

    public int getPomodoroNumber() {
        return pomodoroNumber;
    }

}
