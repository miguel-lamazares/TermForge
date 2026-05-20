package com.Miguel_Lamazares.TermForge.Animation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


import com.Miguel_Lamazares.TermForge.Core.*;
import com.Miguel_Lamazares.TermForge.Validator.*;

public class CornerAnimator {

    public static Thread start(int x1, int x2, int y1, int y2,
            int fps, String framesDir) {
        if (x2 < x1 || y2 < y1)
            throw new IllegalArgumentException("invalid region");

        if (fps <= 0)
            fps = 30;

        final int width = x2 - x1 + 1;
        final int height = y2 - y1 + 1;
        final long frameNanos = 1_000_000_000L / fps;

        final List<Path> frames;
        try {
            frames = listFrames(framesDir);
        } catch (IOException e) {
            throw new RuntimeException("cannot read frames dir: " + framesDir, e);
        }
        if (frames.isEmpty())
            throw new IllegalStateException("no .asc frames in " + framesDir);

        Thread t = new Thread(() -> {
            int idx = 0;
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    long start = System.nanoTime();
                    // Bloco sincronizado: ninguém mais escreve enquanto desenhamos o frame
                    synchronized (LOCK) {
                        drawFrame(frames.get(idx), x1, y1, width, height);
                    }
                    idx = (idx + 1) % frames.size();
                    long sleep = frameNanos - (System.nanoTime() - start);
                    if (sleep > 0) {
                        try {
                            Thread.sleep(sleep / 1_000_000L,
                                    (int) (sleep % 1_000_000L));
                        } catch (InterruptedException ie) {
                            return;
                        }
                    }
                }
            } catch (Exception ignored) {
            }
        }, "animator");
        t.setDaemon(true);
        t.start();
        return t;
    }

    public static void stop(Thread t) {
        if (t != null)
            t.interrupt();
    }

    private static List<Path> listFrames(String dir) throws IOException {
        List<Path> out = new ArrayList<>();
        try (var stream = Files.list(Paths.get(dir))) {
            stream.filter(p -> p.toString().toLowerCase().endsWith(".asc"))
                    .sorted(Comparator.naturalOrder())
                    .forEach(out::add);
        }
        return out;
    }

    private static void drawFrame(Path frame, int x1, int y1,
            int width, int height) throws IOException {
        String content = new String(Files.readAllBytes(frame));
        String[] lines = content.split("\n", -1);
        StringBuilder sb = new StringBuilder(content.length() + 64);

        int rows = Math.min(height, lines.length);

        for (int i = 0; i < rows; i++) {
            // Remove as cores para não poluir a região (pode manter se quiser,
            // mas aí precisa de tratamento seguro de truncamento)
            String line = stripAnsi(lines[i]);
            // Trunca na largura exata
            if (line.length() > width)
                line = line.substring(0, width);

            sb.append("\033[").append(y1 + i).append(';').append(x1).append('H');
            sb.append(line);
            // Preenche o resto da linha com espaços para "limpar" o fundo
            if (line.length() < width)
                sb.append(" ".repeat(width - line.length()));
        }

        // Limpa linhas restantes se o frame for menor que a altura
        for (int i = rows; i < height; i++) {
            sb.append("\033[").append(y1 + i).append(';').append(x1).append('H');
            sb.append(" ".repeat(width));
        }

        // Não restaura cursor – cada frame é absoluto
        System.out.print(sb);
        System.out.flush();
    }

    private static String stripAnsi(String text) {
        // Remove sequências do tipo CSI ... m (cores)
        return text.replaceAll("\u001B\\[[;\\d]*m", "");
    }
}
