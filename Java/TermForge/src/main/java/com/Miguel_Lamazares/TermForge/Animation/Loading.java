package com.Miguel_Lamazares.TermForge.Animation;

import com.Miguel_Lamazares.TermForge.Core.*;
import com.Miguel_Lamazares.TermForge.Validator.*;

import java.util.concurrent.atomic.AtomicBoolean;

public class Loading {

    public static void printProgressBar(int current, int total, int barLength, String emptySpace, String filledSpace) {

        if (filledSpace == null || filledSpace.isEmpty()) {
            filledSpace = "∎";
        }

        if (emptySpace == null || emptySpace.isEmpty()) {
            emptySpace = ".";
        }

        if (total <= 0) {
            System.out.print("\r[..........] ??%");
            System.out.flush();
            return;
        }

        double fraction = (double) current / total;
        int filled = (int) (barLength * fraction);

        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < barLength; i++) {
            if (i < filled) {
                bar.append(filledSpace);
            } else {
                bar.append(emptySpace);
            }
        }
        bar.append("]");

        int percent = (int) (fraction * 100);

        System.out.print("\r" + bar + " " + percent + "% " + current + "/" + total);

        if (current == total) {
            System.out.println();
        }
        System.out.flush();
    }

    public static void ProgressBar(int current, int total, int barLength, String emptySpace, String filledSpace) {

        if (filledSpace == null || filledSpace.isEmpty()) {
            filledSpace = "∎";
        }

        if (emptySpace == null || emptySpace.isEmpty()) {
            emptySpace = ".";
        }

        if (barLength <= 0)
            barLength = 20;
        double progress = (double) current / total;
        int filled = (int) (barLength * progress);
        StringBuilder bar = new StringBuilder();
        for (int i = 0; i < filled; i++)
            bar.append(filledSpace);
        for (int i = 0; i < barLength - filled; i++)
            bar.append(emptySpace);
        double pct = progress * 100;
        System.out.printf("\r[%s] %.2f%% %d/%d :) ", bar, pct, current, total);
        System.out.flush();
        if (current == total)
            System.out.println();
    }

    public static void progressMadeByApe(int current, int total, int bar_length) {

        progress = current / total;
        filled_length = (bar_length * progress);
        bar = '∎' * filled_length + '.' * (bar_length - filled_length);
        por = current / total * 100;
        System.out.print("\r[" + bar + "] " + por + "% " + current + "/" + total + "");
        System.out.flush();
        if (current == total) {
            System.out.println();
        }

    }

    
}
