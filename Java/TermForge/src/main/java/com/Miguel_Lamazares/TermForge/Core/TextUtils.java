package com.Miguel_Lamazares.TermForge.Core;

import com.Miguel_Lamazares.TermForge.Animation.*;
import com.Miguel_Lamazares.TermForge.Validator.*;

import java.lang.management.ManagementFactory;

public static class TextUtils {
    public static class Style {
        public static final String RESET = "\033[0m";
        public static final String BOLD = "\033[1m";
        public static final String DIM = "\033[2m";
        public static final String ITALIC = "\033[3m";
        public static final String UNDERLINE = "\033[4m";
        public static final String BLINK = "\033[5m";
        public static final String REVERSE = "\033[7m";
        public static final String HIDDEN = "\033[8m";
        public static final String STRIKE = "\033[9m";
    }

    public static String box(String text, String style, int padding, String color) {
        String s;
        switch (style == null ? "rounded" : style) {
            case "single":
                s = "┌─┐│└─┘│";
                break;
            case "double":
                s = "╔═╗║╚═╝║";
                break;
            case "heavy":
                s = "┏━┓┃┗━┛┃";
                break;
            case "ascii":
                s = "+-+|+-+|";
                break;
            default:
                s = "╭─╮│╰─╯│";
                break;
        }
        char tl = s.charAt(0), h = s.charAt(1), tr = s.charAt(2), r = s.charAt(3);
        char bl = s.charAt(4), br = s.charAt(6), l = s.charAt(7);

        String[] lines = text.split("\n");
        int width = 0;
        for (String ln : lines)
            width = Math.max(width, stripAnsi(ln).length());

        String pad = " ".repeat(padding);
        String c = color == null ? "" : color;
        String rst = color == null ? "" : Style.RESET;

        StringBuilder out = new StringBuilder();
        out.append(c).append(tl).append(String.valueOf(h).repeat(width + padding * 2)).append(tr).append(rst)
                .append('\n');
        for (String ln : lines) {
            int cleanLen = stripAnsi(ln).length();
            out.append(c).append(l).append(rst).append(pad).append(ln)
                    .append(" ".repeat(width - cleanLen)).append(pad).append(c).append(r).append(rst).append('\n');
        }
        out.append(c).append(bl).append(String.valueOf(h).repeat(width + padding * 2)).append(br).append(rst);
        return out.toString();
    }

    public static String hr(char ch, int width, String color) {
        String line = String.valueOf(ch).repeat(width);
        return color == null ? line : color + line + Style.RESET;
    }

    public static String table(String[] headers, String[][] rows, String headerColor) {
        int cols = headers.length;
        int[] widths = new int[cols];
        for (int i = 0; i < cols; i++)
            widths[i] = headers[i].length();
        for (String[] row : rows)
            for (int i = 0; i < cols; i++)
                widths[i] = Math.max(widths[i], row[i].length());

        StringBuilder out = new StringBuilder();
        out.append("┌─");
        for (int i = 0; i < cols; i++) {
            out.append("─".repeat(widths[i]));
            out.append(i == cols - 1 ? "─┐\n" : "─┬─");
        }
        out.append(headerColor != null ? headerColor : "");
        out.append(formatRow(headers, widths));
        out.append(headerColor != null ? Style.RESET : "").append('\n');
        out.append("├─");
        for (int i = 0; i < cols; i++) {
            out.append("─".repeat(widths[i]));
            out.append(i == cols - 1 ? "─┤\n" : "─┼─");
        }
        for (String[] row : rows)
            out.append(formatRow(row, widths)).append('\n');
        out.append("└─");
        for (int i = 0; i < cols; i++) {
            out.append("─".repeat(widths[i]));
            out.append(i == cols - 1 ? "─┘" : "─┴─");
        }
        return out.toString();
    }

    private static String formatRow(String[] row, int[] widths) {
        StringBuilder sb = new StringBuilder("│ ");
        for (int i = 0; i < row.length; i++) {
            sb.append(padRight(row[i], widths[i]));
            sb.append(i == row.length - 1 ? " │" : " │ ");
        }
        return sb.toString();
    }

    private static String padRight(String s, int n) {
        if (s.length() >= n)
            return s;
        return s + " ".repeat(n - s.length());
    }

    public static String stripAnsi(String text) {
        return text.replaceAll("\\x1B\\[[0-9;]*[A-Za-z]", "");
    }

    public static void printCentralizedText(String text, int terminalWidth) {
        for (String line : text.split("\n")) {
            int len = stripAnsi(line).length();
            int pad = Math.max(0, (terminalWidth - len) / 2);
            System.out.println(" ".repeat(pad) + line);
        }
    }

    public static void beep() {
        System.out.print("\007");
        System.out.flush();
    }

    public static class AwtBeep {
        public static void beep() {
            try { java.awt.Toolkit.getDefaultToolkit().beep(); }
            catch (Throwable ignored) { System.out.print("\007"); System.out.flush(); }
        }
    }

    public static class SysInfo {
        public static long uptimeMs()       { return ManagementFactory.getRuntimeMXBean().getUptime(); }
        public static int  threadCount()    { return ManagementFactory.getThreadMXBean().getThreadCount(); }
        public static int  cores()          { return Runtime.getRuntime().availableProcessors(); }
        public static long heapUsedBytes()  { return ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed(); }
        public static long heapMaxBytes()   { return ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax(); }

        public static String summary() {
            long up = uptimeMs() / 1000;
            double heapMb = heapUsedBytes() / 1024.0 / 1024.0;
            double maxMb  = heapMaxBytes()  / 1024.0 / 1024.0;
            return String.format("uptime=%ds  cores=%d  threads=%d  heap=%.1f/%.1fMB",
                    up, cores(), threadCount(), heapMb, maxMb);
        }
    }
}
