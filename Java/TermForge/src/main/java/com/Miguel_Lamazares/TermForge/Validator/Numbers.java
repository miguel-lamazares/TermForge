package com.Miguel_Lamazares.TermForge.Validator;

import com.Miguel_Lamazares.TermForge.Animation.*;
import com.Miguel_Lamazares.TermForge.Core.*;


import java.util.Scanner;

public class Numbers {

    public static int onlyNumbers(java.util.Scanner scanner) {
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

    public static int numbersBetween(java.util.Scanner scanner, int minvalue, int maxvalue) {
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
