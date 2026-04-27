import { useState } from "react";
import { cn } from "@/lib/utils";

type Lang = "python" | "java" | "ruby";

const SNIPPETS: Record<Lang, { label: string; icon: string; code: string }> = {
  python: {
    label: "Python",
    icon: "🐍",
    code: `from Terminal import (
    Clear_all, rainbow_text, gradient_text,
    box, Spinner, glitch, wave, RGB, menu
)

Clear_all()
print(rainbow_text("Welcome to my CLI!"))
print(box("Choose wisely.", style="rounded",
          color=RGB.fg(0, 220, 255)))

with Spinner("Loading universe", style="moon"):
    do_heavy_work()

choice = menu("Pick one:", ["New game", "Continue", "Quit"])
glitch("LET'S GO", duration=1.0)`,
  },
  java: {
    label: "Java",
    icon: "☕",
    code: `TerminalLib.clearAll();
System.out.println(TerminalLib.rainbowText("Welcome!"));
System.out.println(TerminalLib.box(
    "Choose wisely.", "rounded", 1,
    TerminalLib.RGB.fg(0, 220, 255)));

TerminalLib.Spinner sp = new TerminalLib.Spinner(
    "Loading", TerminalLib.Spinner.DOTS, null
).start();
heavyWork();
sp.stop();

TerminalLib.glitch("LET'S GO", 1000, 0.3);
TerminalLib.wave("~ have fun ~", 2, 50);`,
  },
  ruby: {
    label: "Ruby",
    icon: "💎",
    code: `require_relative 'Terminal'

clear_all
puts rainbow_text("Welcome!")
puts box("Choose wisely.",
         style: :rounded,
         color: RGB.fg(0, 220, 255))

Spinner.run(message: "Loading", style: :moon) do
  heavy_work
end

glitch("LET'S GO", duration: 1.0)
wave("~ have fun ~", cycles: 2)`,
  },
};

export const CodeTabs = () => {
  const [active, setActive] = useState<Lang>("python");
  const snippet = SNIPPETS[active];

  return (
    <div className="rounded-xl border border-border bg-card/60 overflow-hidden shadow-terminal">
      <div className="flex border-b border-border bg-muted/30">
        {(Object.keys(SNIPPETS) as Lang[]).map((lang) => (
          <button
            key={lang}
            onClick={() => setActive(lang)}
            className={cn(
              "px-5 py-3 font-mono text-sm transition-all border-b-2",
              active === lang
                ? "text-primary border-primary bg-background/50"
                : "text-muted-foreground border-transparent hover:text-foreground"
            )}
          >
            <span className="mr-2">{SNIPPETS[lang].icon}</span>
            {SNIPPETS[lang].label}
          </button>
        ))}
      </div>
      <pre className="p-6 font-mono text-sm leading-relaxed overflow-x-auto text-foreground/90">
        <code>{snippet.code}</code>
      </pre>
    </div>
  );
};
