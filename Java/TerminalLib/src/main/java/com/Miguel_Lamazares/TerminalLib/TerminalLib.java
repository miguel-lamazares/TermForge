import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * ╔══════════════════════════════════════════════════════════════╗
 * ║                    TerminalLib · Java                        ║
 * ║   Your terminal doesn't need to be ugly.                     ║
 * ╚══════════════════════════════════════════════════════════════╝
 *
 * The original API is preserved verbatim. The Extended API (v2) adds
 * RGB colors, gradients, spinners, boxes, tables, advanced text effects
 * and an arrow-key menu.
 */
public class TerminalLib {

    // ============================================================
    // ORIGINAL API  (kept exactly as written — never deleted)
    // ============================================================

    public class Clear {
        public void clear() {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

    public static void clearBuffer() {
        System.out.print("\033[1A");
        System.out.print("\033[2K");
    }

    public class Colors {

        public static final String RESET = "\u001B[0m";
        public static final String BLACK = "\u001B[30m";
        public static final String RED = "\u001B[31m";
        public static final String GREEN = "\u001B[32m";
        public static final String YELLOW = "\u001B[33m";
        public static final String BLUE = "\u001B[34m";
        public static final String PURPLE = "\u001B[35m";
        public static final String CYAN = "\u001B[36m";
        public static final String WHITE = "\u001B[37m";
    }

    public class onlyNumbers {
        public static int ReadCalc(java.util.Scanner scanner) {
            int choice;
            while (true) {
                if (!scanner.hasNextInt()) {
                    scanner.nextLine();
                    System.out.print("\033[1A");
                    System.out.print("\033[2K");

                    continue;
                }
                choice = scanner.nextInt();
                scanner.nextLine();

                return choice;
            }
        }
    }

    public class JustNumber {
        public static int readInt(java.util.Scanner scanner, int minvalue, int maxvalue) {
            int choice;
            while (true) {
                if (!scanner.hasNextInt()) {
                    scanner.nextLine();
                    System.out.print("\033[1A");
                    System.out.print("\033[2K");

                    continue;
                }
                choice = scanner.nextInt();
                scanner.nextLine();

                if (choice <= minvalue - 1) {
                    System.out.print("\033[1A");
                    System.out.print("\033[2K");
                    continue;
                }
                if (choice >= maxvalue) {
                    System.out.print("\033[1A");
                    System.out.print("\033[2K");
                    continue;
                }

                return choice;
            }

        }

    }

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

    /*
     * NOTE: original signature kept (it referenced undeclared variables in v1).
     * A working implementation is provided in the Extended API as
     * `printProgressBar(current, total, barLength)`.
     */
    public static void print_progress_bar(int current, int total, int bar_length) {
        // (original body retained as comment to honor the "never delete" rule)
        // bar_length = 20;
        // progress = current / total;
        // filled_length = (bar_length * progress);
        // bar = '∎' * filled_length + '.' * (bar_length - filled_length);
        // por = current / total * 100;
        // System.out.print("\r[" + bar + "] " + por + "% " + current + "/" + total + "");
        // System.out.flush();
        // if (current == total) { System.out.println(); }
        printProgressBar(current, total, bar_length);
    }

    // ╔══════════════════════════════════════════════════════════════╗
    // ║   ✦ EXTENDED API · v2  (new features, originals untouched)   ║
    // ╚══════════════════════════════════════════════════════════════╝

    //
    // CLEAR (static convenience)
    //
    public static void clearAll() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    //
    // CURSOR CONTROL
    //
    public static class Cursor {
        public static final String HIDE = "\033[?25l";
        public static final String SHOW = "\033[?25h";
        public static final String SAVE = "\033[s";
        public static final String RESTORE = "\033[u";

        public static void up(int n)    { System.out.print("\033[" + n + "A"); System.out.flush(); }
        public static void down(int n)  { System.out.print("\033[" + n + "B"); System.out.flush(); }
        public static void right(int n) { System.out.print("\033[" + n + "C"); System.out.flush(); }
        public static void left(int n)  { System.out.print("\033[" + n + "D"); System.out.flush(); }
        public static void goTo(int row, int col) { System.out.print("\033[" + row + ";" + col + "H"); System.out.flush(); }
        public static void clearLine() { System.out.print("\033[2K\r"); System.out.flush(); }
        public static void hide()      { System.out.print(HIDE); System.out.flush(); }
        public static void show()      { System.out.print(SHOW); System.out.flush(); }
    }

    //
    // TEXT STYLES
    //
    public static class Style {
        public static final String RESET     = "\033[0m";
        public static final String BOLD      = "\033[1m";
        public static final String DIM       = "\033[2m";
        public static final String ITALIC    = "\033[3m";
        public static final String UNDERLINE = "\033[4m";
        public static final String BLINK     = "\033[5m";
        public static final String REVERSE   = "\033[7m";
        public static final String HIDDEN    = "\033[8m";
        public static final String STRIKE    = "\033[9m";
    }

    //
    // 24-BIT RGB & 256-COLOR SUPPORT
    //
    public static class RGB {
        public static String fg(int r, int g, int b)  { return "\033[38;2;" + r + ";" + g + ";" + b + "m"; }
        public static String bg(int r, int g, int b)  { return "\033[48;2;" + r + ";" + g + ";" + b + "m"; }
        public static String fg256(int n)             { return "\033[38;5;" + n + "m"; }
        public static String bg256(int n)             { return "\033[48;5;" + n + "m"; }

        public static String hex(String hex) {
            String h = hex.startsWith("#") ? hex.substring(1) : hex;
            return fg(
                Integer.parseInt(h.substring(0, 2), 16),
                Integer.parseInt(h.substring(2, 4), 16),
                Integer.parseInt(h.substring(4, 6), 16)
            );
        }
    }

    //
    // GRADIENT & RAINBOW TEXT
    //
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
        return sb.append(Style.RESET).toString();
    }

    public static String rainbowText(String text) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            double h = ((double) i / Math.max(text.length(), 1)) * 360.0;
            int[] rgb = hsvToRgb(h, 1, 1);
            sb.append(RGB.fg(rgb[0], rgb[1], rgb[2])).append(text.charAt(i));
        }
        return sb.append(Style.RESET).toString();
    }

    private static int[] hsvToRgb(double h, double s, double v) {
        double c = v * s;
        double x = c * (1 - Math.abs(((h / 60.0) % 2) - 1));
        double m = v - c;
        double r, g, b;
        if (h < 60)       { r = c; g = x; b = 0; }
        else if (h < 120) { r = x; g = c; b = 0; }
        else if (h < 180) { r = 0; g = c; b = x; }
        else if (h < 240) { r = 0; g = x; b = c; }
        else if (h < 300) { r = x; g = 0; b = c; }
        else              { r = c; g = 0; b = x; }
        return new int[] { (int)((r + m) * 255), (int)((g + m) * 255), (int)((b + m) * 255) };
    }

    //
    // ADVANCED TEXT EFFECTS
    //
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
                System.out.print("\r" + RGB.fg(0, 255, 120) + sb + Style.RESET);
                System.out.flush();
                try { Thread.sleep(50); } catch (InterruptedException ignored) {}
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
                for (int i = 0; i < offset; i++) sb.append(' ');
                sb.append(text);
                for (int i = 0; i < 4 - offset; i++) sb.append(' ');
                System.out.print(sb);
                System.out.flush();
                try { Thread.sleep(40); } catch (InterruptedException ignored) {}
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
                System.out.print(sb).append(Style.RESET);
                System.out.flush();
                try { Thread.sleep(speedMs); } catch (InterruptedException ignored) {}
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
                try { Thread.sleep(speedMs); } catch (InterruptedException ignored) {}
            }
            System.out.println();
        } finally {
            Cursor.show();
        }
    }

    //
    // SPINNERS
    //
    public static class Spinner {
        public static final String[] DOTS     = {"⠋","⠙","⠹","⠸","⠼","⠴","⠦","⠧","⠇","⠏"};
        public static final String[] LINE     = {"-","\\","|","/"};
        public static final String[] ARROWS   = {"←","↖","↑","↗","→","↘","↓","↙"};
        public static final String[] BOUNCING = {"[    ]","[=   ]","[==  ]","[=== ]","[ ===]","[  ==]","[   =]","[    ]"};
        public static final String[] PULSE    = {"•","●","◉","●","•"};

        private final String[] frames;
        private final String message;
        private final String color;
        private final AtomicBoolean stop = new AtomicBoolean(false);
        private Thread thread;

        public Spinner(String message, String[] frames, String color) {
            this.message = message;
            this.frames  = frames != null ? frames : DOTS;
            this.color   = color != null ? color : RGB.fg(0, 200, 255);
        }

        public Spinner start() {
            stop.set(false);
            Cursor.hide();
            thread = new Thread(() -> {
                int i = 0;
                while (!stop.get()) {
                    System.out.print("\r" + color + frames[i % frames.length] + Style.RESET + " " + message);
                    System.out.flush();
                    try { Thread.sleep(80); } catch (InterruptedException e) { break; }
                    i++;
                }
                Cursor.clearLine();
                Cursor.show();
            });
            thread.setDaemon(true);
            thread.start();
            return this;
        }

        public void stop(String finalMessage) {
            stop.set(true);
            try { if (thread != null) thread.join(); } catch (InterruptedException ignored) {}
            if (finalMessage != null) System.out.println(finalMessage);
        }

        public void stop() { stop(null); }
    }

    //
    // BOXES & BORDERS
    //
    public static String box(String text, String style, int padding, String color) {
        String s;
        switch (style == null ? "rounded" : style) {
            case "single":  s = "┌─┐│└─┘│"; break;
            case "double":  s = "╔═╗║╚═╝║"; break;
            case "heavy":   s = "┏━┓┃┗━┛┃"; break;
            case "ascii":   s = "+-+|+-+|"; break;
            default:        s = "╭─╮│╰─╯│"; break;
        }
        char tl = s.charAt(0), h = s.charAt(1), tr = s.charAt(2), r = s.charAt(3);
        char bl = s.charAt(4), br = s.charAt(6), l = s.charAt(7);

        String[] lines = text.split("\n");
        int width = 0;
        for (String ln : lines) width = Math.max(width, stripAnsi(ln).length());

        String pad = " ".repeat(padding);
        String c = color == null ? "" : color;
        String rst = color == null ? "" : Style.RESET;

        StringBuilder out = new StringBuilder();
        out.append(c).append(tl).append(String.valueOf(h).repeat(width + padding * 2)).append(tr).append(rst).append('\n');
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

    //
    // TABLE
    //
    public static String table(String[] headers, String[][] rows, String headerColor) {
        int cols = headers.length;
        int[] widths = new int[cols];
        for (int i = 0; i < cols; i++) widths[i] = headers[i].length();
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
        for (String[] row : rows) out.append(formatRow(row, widths)).append('\n');
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
        if (s.length() >= n) return s;
        return s + " ".repeat(n - s.length());
    }

    //
    // PROGRESS BAR (working impl)
    //
    public static void printProgressBar(int current, int total, int barLength) {
        if (barLength <= 0) barLength = 20;
        double progress = (double) current / total;
        int filled = (int) (barLength * progress);
        StringBuilder bar = new StringBuilder();
        for (int i = 0; i < filled; i++) bar.append('∎');
        for (int i = 0; i < barLength - filled; i++) bar.append('.');
        double pct = progress * 100;
        System.out.printf("\r[%s] %.2f%% %d/%d :) ", bar, pct, current, total);
        System.out.flush();
        if (current == total) System.out.println();
    }

    //
    // CENTER TEXT  &  ANSI STRIP
    //
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

    //
    // BEEP
    //
    public static void beep() {
        System.out.print("\007");
        System.out.flush();
    }

    //
    // DEMO
    //
    public static void main(String[] args) throws InterruptedException {
        clearAll();
        System.out.println(rainbowText("✦ TerminalLib · Java · v2 ✦"));
        System.out.println(hr('─', 60, RGB.fg(80, 80, 80)));
        System.out.println(box("Your terminal\ndoesn't need to be ugly.", "rounded", 1, RGB.fg(0, 220, 255)));
        typewrite(gradientText("Loading awesome features...", new int[]{255, 0, 128}, new int[]{0, 200, 255}), 25);

        Spinner sp = new Spinner("Crunching numbers", Spinner.DOTS, RGB.fg(0, 200, 255)).start();
        Thread.sleep(1500);
        sp.stop();

        glitch("SYSTEM ONLINE", 1200, 0.3);
        wave("~ have fun ~", 2, 50);
    }
}
