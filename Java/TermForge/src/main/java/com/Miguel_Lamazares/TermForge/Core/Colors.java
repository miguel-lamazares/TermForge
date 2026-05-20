package com.Miguel_Lamazares.TermForge.Core;

import com.Miguel_Lamazares.TermForge.Animation.*;
import com.Miguel_Lamazares.TermForge.Validator.*;

public class Colors {
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

    public static class RGB {
        public static String fg(int r, int g, int b) {
            return "\033[38;2;" + r + ";" + g + ";" + b + "m";
        }

        public static String bg(int r, int g, int b) {
            return "\033[48;2;" + r + ";" + g + ";" + b + "m";
        }

        public static String fg256(int n) {
            return "\033[38;5;" + n + "m";
        }

        public static String bg256(int n) {
            return "\033[48;5;" + n + "m";
        }

        public static String hex(String hex) {
            String h = hex.startsWith("#") ? hex.substring(1) : hex;
            return fg(
                    Integer.parseInt(h.substring(0, 2), 16),
                    Integer.parseInt(h.substring(2, 4), 16),
                    Integer.parseInt(h.substring(4, 6), 16));
        }
    }
}