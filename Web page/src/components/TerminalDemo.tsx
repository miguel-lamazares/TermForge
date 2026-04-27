import { useEffect, useState } from "react";

interface Line {
  prompt?: string;
  text: string;
  className?: string;
  delay?: number;
}

const SCRIPT: Line[] = [
  { prompt: "$", text: "python Terminal.py", className: "text-foreground" },
  { text: "✦ TerminalLib · Python · v2 ✦", className: "text-gradient-rainbow font-bold" },
  { text: "────────────────────────────────────────────", className: "text-muted-foreground" },
  { text: "╭──────────────────────────────╮", className: "text-primary" },
  { text: "│  Your terminal               │", className: "text-primary" },
  { text: "│  doesn't need to be ugly.    │", className: "text-primary" },
  { text: "╰──────────────────────────────╯", className: "text-primary" },
  { text: "Loading awesome features...", className: "text-gradient-primary" },
  { text: "⠹ Crunching numbers", className: "text-secondary" },
  { text: "✓ SYSTEM ONLINE", className: "text-secondary font-bold" },
  { text: "~ have fun ~", className: "text-accent" },
];

export const TerminalDemo = () => {
  const [visible, setVisible] = useState(0);

  useEffect(() => {
    if (visible >= SCRIPT.length) {
      const reset = setTimeout(() => setVisible(0), 4000);
      return () => clearTimeout(reset);
    }
    const t = setTimeout(() => setVisible((v) => v + 1), 450);
    return () => clearTimeout(t);
  }, [visible]);

  return (
    <div className="relative rounded-xl border border-border bg-gradient-terminal shadow-terminal overflow-hidden scanlines">
      {/* Title bar */}
      <div className="flex items-center gap-2 px-4 py-3 border-b border-border bg-muted/40">
        <span className="w-3 h-3 rounded-full bg-destructive/80" />
        <span className="w-3 h-3 rounded-full bg-yellow-500/80" />
        <span className="w-3 h-3 rounded-full bg-secondary/80" />
        <span className="ml-3 text-xs text-muted-foreground font-mono">
          ~/projects/terminal-lib — demo
        </span>
      </div>

      {/* Body */}
      <div className="font-mono text-sm md:text-base p-5 md:p-7 min-h-[360px] leading-relaxed">
        {SCRIPT.slice(0, visible).map((line, i) => (
          <div
            key={i}
            className="animate-fade-in whitespace-pre"
          >
            {line.prompt && (
              <span className="text-secondary mr-2">{line.prompt}</span>
            )}
            <span className={line.className}>{line.text}</span>
          </div>
        ))}
        <div className="text-primary caret inline-block" />
      </div>
    </div>
  );
};
