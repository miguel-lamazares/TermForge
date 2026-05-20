package com.Miguel_Lamazares.TermForge.Core;

import com.Miguel_Lamazares.TermForge.Animation.*;
import com.Miguel_Lamazares.TermForge.Validator.*;

public class Cleaners {

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

    public static void clearAll() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
