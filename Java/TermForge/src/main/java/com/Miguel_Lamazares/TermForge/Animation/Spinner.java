package com.Miguel_Lamazares.TermForge.Animation;

import com.Miguel_Lamazares.TermForge.Core.*;
import com.Miguel_Lamazares.TermForge.Validator.*;

import java.util.concurrent.atomic.AtomicBoolean;

public class Spinner {
    public static class Spinner {
        public static final String[] DOTS = { "⠋", "⠙", "⠹", "⠸", "⠼", "⠴", "⠦", "⠧", "⠇", "⠏" };
        public static final String[] LINE = { "-", "\\", "|", "/" };
        public static final String[] ARROWS = { "←", "↖", "↑", "↗", "→", "↘", "↓", "↙" };
        public static final String[] BOUNCING = { "[    ]", "[=   ]", "[==  ]", "[=== ]", "[ ===]", "[  ==]", "[   =]",
                "[    ]" };
        public static final String[] PULSE = { "•", "●", "◉", "●", "•" };

        private final String[] frames;
        private final String message;
        private final String color;
        private final AtomicBoolean stop = new AtomicBoolean(false);
        private Thread thread;

        public Spinner(String message, String[] frames, String color) {
            this.message = message;
            this.frames = frames != null ? frames : DOTS;
            this.color = color != null ? color : RGB.fg(0, 200, 255);
        }

        public Spinner start() {
            stop.set(false);
            Cursor.hide();
            thread = new Thread(() -> {
                int i = 0;
                while (!stop.get()) {
                    System.out.print("\r" + color + frames[i % frames.length] + Style.RESET + " " + message);
                    System.out.flush();
                    try {
                        Thread.sleep(80);
                    } catch (InterruptedException e) {
                        break;
                    }
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
            try {
                if (thread != null)
                    thread.join();
            } catch (InterruptedException ignored) {
            }
            if (finalMessage != null)
                System.out.println(finalMessage);
        }

        public void stop() {
            stop(null);
        }
    }
}
