package com.alcamech.jomo;

import java.text.DecimalFormat;

public class Countdown {
    private static long totalTimeBySeconds;
    private static long perSec = 1000; //1000ms = 1 sec

    public static void remainTime(long timeBySec) {
        long min = timeBySec/60;
        long sec = timeBySec%60;

        DecimalFormat df = new DecimalFormat("00"); //min and sec in two digits
        System.out.println(df.format(min)+":"+df.format(sec));
    }

    public static void bySleep(int totalTime) throws InterruptedException {
        totalTimeBySeconds = totalTime * 60L;

        for (long i = totalTimeBySeconds; i >= 0; i--) {
            Thread.sleep(perSec); //wait a second
            remainTime(i);
        }
    }
}
