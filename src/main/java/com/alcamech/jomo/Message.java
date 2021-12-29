package com.alcamech.jomo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Message {
    public static boolean isContinue(String message) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true) {
            System.out.println(message+" Y/N ");
            String reply = br.readLine();
            if(reply.equals("Y") || reply.equals("y")) {
                return true;
            } else if (reply.equals("N") || reply.equals("n")) {
                return false;
            }
        }
    }

    public static void startPomo() {
        System.out.println("Begin work.");
    }

    public static void startShortBreak() {
        System.out.println("Begin a short break.");
    }

    public static void startLongBreak() {
        System.out.println("Begin a long break.");
    }

    public static void showCircuitNumber(int n) {
        System.out.println("Circuit Number: "+n);
    }
}
