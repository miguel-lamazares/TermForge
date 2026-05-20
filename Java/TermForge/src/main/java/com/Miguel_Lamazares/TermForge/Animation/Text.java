package com.Miguel_Lamazares.TermForge.Animation;

import com.Miguel_Lamazares.TermForge.Core.*;
import com.Miguel_Lamazares.TermForge.Validator.*;

import java.util.Random;                       

public class Text {

    public static void typewrite(String text, long speedMillis) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            System.out.flush();
            try {
                Thread.sleep(speedMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }

    public static String gradientText(String text, int[] startRgb, int[] endRgb) {
        StringBuilder sb = new StringBuilder();
        int n = Math.max(text.length() - 1, 1);
        for (int i = 0; i < text.length(); i++) {
            double t = (double) i / n;
            int r = (int) (startRgb[0] + (endRgb[0] - startRgb[0]) * t);
            int g = (int) (startRgb[1] + (endRgb[1] - startRgb[1]) * t);
            int b = (int) (startRgb[2] + (endRgb[2] - startRgb[2]) * t);
            sb.append(RGB.fg(r, g, b)).append(text.charAt(i));
        }
        return sb.append(Core.TextUtils.Style.RESET).toString();
    }

    public static String rainbowText(String text) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            double h = ((double) i / Math.max(text.length(), 1)) * 360.0;
            int[] rgb = hsvToRgb(h, 1, 1);
            sb.append(RGB.fg(rgb[0], rgb[1], rgb[2])).append(text.charAt(i));
        }
        return sb.append(Core.TextUtils.Style.RESET).toString();
    }

    private static int[] hsvToRgb(double h, double s, double v) {
        double c = v * s;
        double x = c * (1 - Math.abs(((h / 60.0) % 2) - 1));
        double m = v - c;
        double r, g, b;
        if (h < 60) {
            r = c;
            g = x;
            b = 0;
        } else if (h < 120) {
            r = x;
            g = c;
            b = 0;
        } else if (h < 180) {
            r = 0;
            g = c;
            b = x;
        } else if (h < 240) {
            r = 0;
            g = x;
            b = c;
        } else if (h < 300) {
            r = x;
            g = 0;
            b = c;
        } else {
            r = c;
            g = 0;
            b = x;
        }
        return new int[] { (int) ((r + m) * 255), (int) ((g + m) * 255), (int) ((b + m) * 255) };
    }

    public static void glitch(String text, long durationMs, double intensity) {
        String pool = "!@#$%^&*()_+-=[]{}|;:,.<>?/~`";
        Random rnd = new Random();
        long end = System.currentTimeMillis() + durationMs;
        Cursor.hide();
        try {
            while (System.currentTimeMillis() < end) {
                StringBuilder sb = new StringBuilder();
                for (char c : text.toCharArray()) {
                    sb.append(rnd.nextDouble() < intensity ? pool.charAt(rnd.nextInt(pool.length())) : c);
                }
                System.out.print("\r" + RGB.fg(0, 255, 120) + sb + Core.TextUtils.Style.RESET);
                System.out.flush();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ignored) {
                }
            }
            System.out.println("\r" + text + "    ");
        } finally {
            Cursor.show();
        }
    }

    public static void shake(String text, long durationMs) {
        Random rnd = new Random();
        long end = System.currentTimeMillis() + durationMs;
        Cursor.hide();
        try {
            while (System.currentTimeMillis() < end) {
                int offset = rnd.nextInt(5);
                StringBuilder sb = new StringBuilder("\r");
                for (int i = 0; i < offset; i++)
                    sb.append(' ');
                sb.append(text);
                for (int i = 0; i < 4 - offset; i++)
                    sb.append(' ');
                System.out.print(sb);
                System.out.flush();
                try {
                    Thread.sleep(40);
                } catch (InterruptedException ignored) {
                }
            }
            System.out.println("\r" + text + "    ");
        } finally {
            Cursor.show();
        }
    }

    public static void wave(String text, int cycles, long speedMs) {
        Cursor.hide();
        try {
            for (int frame = 0; frame < cycles * 30; frame++) {
                StringBuilder sb = new StringBuilder("\r");
                for (int i = 0; i < text.length(); i++) {
                    double phase = (i + frame) * 0.3;
                    int r = (int) (127 + 127 * Math.sin(phase));
                    int g = (int) (127 + 127 * Math.sin(phase + 2));
                    int b = (int) (127 + 127 * Math.sin(phase + 4));
                    sb.append(RGB.fg(r, g, b)).append(text.charAt(i));
                }
                System.out.print(sb.append(Core.TextUtils.Style.RESET));
                System.out.flush();
                try {
                    Thread.sleep(speedMs);
                } catch (InterruptedException ignored) {
                }
            }
            System.out.println();
        } finally {
            Cursor.show();
        }
    }

    public static void fadeIn(String text, long speedMs) {
        Cursor.hide();
        try {
            for (int i = 1; i <= text.length(); i++) {
                System.out.print("\r" + text.substring(0, i));
                System.out.flush();
                try {
                    Thread.sleep(speedMs);
                } catch (InterruptedException ignored) {
                }
            }
            System.out.println();
        } finally {
            Cursor.show();
        }
    }
}
