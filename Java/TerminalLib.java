public class TerminalLib {

    public class Clear {
        public static void clear() {
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

    public static void print_progress_bar(int current, int total, int bar_length) {
        bar_length = 20;
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
