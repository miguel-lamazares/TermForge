package com.Miguel_Lamazares.TermForge;

import com.Miguel_Lamazares.TermForge.Core.*;
import com.Miguel_Lamazares.TermForge.Animation.*;
import com.Miguel_Lamazares.TermForge.Validator.Numbers;

public class TermForge {
    public static void main(String[] args) throws InterruptedException {
        Cleaner.clearAll();
        System.out.println( rainbowText("✦ TermForge · Java · v2 ✦"));
        System.out.println(hr('─', 60, RGB.fg(80, 80, 80)));
        System.out.println(box("Your terminal\ndoesn't need to be ugly.", "rounded", 1, RGB.fg(0, 220, 255)));
        Text.typewrite(gradientText("Loading awesome features...", new int[] { 255, 0, 128 }, new int[] { 0, 200, 255 }),
                25);

        Text.Spinner sp = new Text.Spinner("Crunching numbers", Text.Spinner.DOTS, RGB.fg(0, 200, 255)).start();
        Thread.sleep(1500);
        sp.stop();

        Text.glitch("SYSTEM ONLINE", 1200, 0.3);
        Text.wave("~ have fun ~", 2, 50);
    }
}
