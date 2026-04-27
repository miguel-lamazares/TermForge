import java.io.Console;
import java.util.regex.Pattern;

public class TerminalLib {

    // -------------------------------------------------
    // UTILS
    // -------------------------------------------------

    public static boolean isTTY() {
        return System.console() != null;
    }

    public static void resetStyle() {
        System.out.print("\u001B[0m");
        System.out.flush();
    }

    // -------------------------------------------------
    // CLEAR TERMINAL
    // -------------------------------------------------

    public static void clearAll() {
        if (!isTTY()) return;
        System.out.print("\u001B[H\u001B[2J");
        System.out.flush();
    }

    // -------------------------------------------------
    // COLORS
    // -------------------------------------------------

    public static class Colors {
        public static final String RESET  = "\u001B[0m";
        public static final String BLACK  = "\u001B[30m";
        public static final String RED    = "\u001B[31m";
        public static final String GREEN  = "\u001B[32m";
        public static final String YELLOW = "\u001B[33m";
        public static final String BLUE   = "\u001B[34m";
        public static final String PURPLE = "\u001B[35m";
        public static final String CYAN   = "\u001B[36m";
        public static final String WHITE  = "\u001B[37m";
    }

    // -------------------------------------------------
    // TYPEWRITE
    // -------------------------------------------------

    public static void typewrite(String text, long millis) throws InterruptedException {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            System.out.flush();
            Thread.sleep(millis);
        }
        System.out.println();
    }

    // -------------------------------------------------
    // PROGRESS BAR
    // -------------------------------------------------

    public static void printProgressBar(int current, int total, int barLength) {
        if (!isTTY() || total <= 0) return;

        double progress = Math.min((double) current / total, 1.0);
        int filled = (int) (barLength * progress);

        String bar = "∎".repeat(filled) + ".".repeat(barLength - filled);
        double percent = progress * 100;

        System.out.printf("\r[%s] %.2f%% %d/%d", bar, percent, current, total);
        System.out.flush();

        if (current >= total) System.out.println();
    }

    // -------------------------------------------------
    // CENTERED TEXT
    // -------------------------------------------------

    public static void printCenteredText(String text) {
        if (!isTTY()) {
            System.out.println(text);
            return;
        }

        int width = 80;
        try {
            width = Integer.parseInt(System.getenv().getOrDefault("COLUMNS", "80"));
        } catch (Exception ignored) {}

        Pattern ansi = Pattern.compile("\u001B\\[[0-9;]*[A-Za-z]");

        for (String line : text.split("\n")) {
            String clean = ansi.matcher(line).replaceAll("");
            int padding = Math.max((width - clean.length()) / 2, 0);
            System.out.println(" ".repeat(padding) + line);
        }
    }

    // -------------------------------------------------
    // RAINBOW LOADING
    // -------------------------------------------------

    public static void rainbowLoading(
            String text,
            int maxDots,
            StopCondition stop,
            long millis
    ) throws InterruptedException {

        if (!isTTY()) return;

        String[] colors = {
                Colors.RED,
                Colors.YELLOW,
                Colors.GREEN,
                Colors.CYAN,
                Colors.BLUE,
                Colors.PURPLE
        };

        int dots = 1;
        int colorIndex = 0;

        try {
            while (!stop.shouldStop()) {
                System.out.print(
                        "\r" + colors[colorIndex] + text + " " +
                        ".".repeat(dots) + Colors.RESET
                );
                System.out.flush();

                Thread.sleep(millis);

                dots++;
                if (dots > maxDots) dots = 1;
                colorIndex = (colorIndex + 1) % colors.length;
            }
        } finally {
            resetStyle();
            System.out.println();
        }
    }

    // -------------------------------------------------
    // SPINNER
    // -------------------------------------------------

    public static void spinBar(StopCondition stop, long millis)
            throws InterruptedException {

        if (!isTTY()) return;

        String[] bars = {"|", "/", "-", "\\"};
        int index = 0;

        try {
            while (!stop.shouldStop()) {
                System.out.print("\r" + bars[index]);
                System.out.flush();
                Thread.sleep(millis);
                index = (index + 1) % bars.length;
            }
        } finally {
            resetStyle();
            System.out.println();
        }
    }

    // -------------------------------------------------
    // STOP INTERFACE
    // -------------------------------------------------

    public interface StopCondition {
        boolean shouldStop();
    }
}
