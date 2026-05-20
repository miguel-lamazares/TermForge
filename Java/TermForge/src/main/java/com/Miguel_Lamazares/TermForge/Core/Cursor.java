package com.Miguel_Lamazares.TermForge.Core;

import com.Miguel_Lamazares.TermForge.Animation.*;
import com.Miguel_Lamazares.TermForge.Validator.*;



public class Cursor {
    public static final String HIDE = "\033[?25l";
    public static final String SHOW = "\033[?25h";
    public static final String SAVE = "\033[s";
    public static final String RESTORE = "\033[u";

    public static void up(int n) {
        System.out.print("\033[" + n + "A");
        System.out.flush();
    }

    public static void down(int n) {
        System.out.print("\033[" + n + "B");
        System.out.flush();
    }

    public static void right(int n) {
        System.out.print("\033[" + n + "C");
        System.out.flush();
    }

    public static void left(int n) {
        System.out.print("\033[" + n + "D");
        System.out.flush();
    }

    public static void goTo(int row, int col) {
        System.out.print("\033[" + row + ";" + col + "H");
        System.out.flush();
    }

    public static void clearLine() {
        System.out.print("\033[2K\r");
        System.out.flush();
    }

    public static void hide() {
        System.out.print(HIDE);
        System.out.flush();
    }

    public static void show() {
        System.out.print(SHOW);
        System.out.flush();
    }
}
