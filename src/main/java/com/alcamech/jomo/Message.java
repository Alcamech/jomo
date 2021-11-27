package com.alcamech.jomo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Message {
    public static boolean isContinue() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true) {
            System.out.println("Ready to keep working? Y/N ");
            String reply = br.readLine();
            if(reply.equals("Y")) {
                return true;
            } else if (reply.equals("N")) {
                return false;
            }
        }
    }

    public static void startPomo() {
        System.out.print("Begin work.\n");
    }

    public static void startShortBreak() {
        System.out.print("Begin a short break.\n");
    }

    public static void startLongBreak() {
        System.out.print("Begin a long break.\n");
    }
}
