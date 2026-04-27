import { TerminalDemo } from "@/components/TerminalDemo";
import { FeatureCard } from "@/components/FeatureCard";
import { CodeTabs } from "@/components/CodeTabs";
import { Button } from "@/components/ui/button";

const FEATURES = [
  { icon: "🎨", title: "24-bit RGB & gradients", description: "From basic ANSI to full RGB, hex colors, smooth gradients and rainbow text out of the box." },
  { icon: "✍️", title: "Text effects", description: "Typewriter, fade-in, glitch, shake, wave, blink, bold, italic, underline, strike." },
  { icon: "⏳", title: "Spinners & progress", description: "Six spinner styles (dots, moon, arrows…) running on a background thread, plus a clean progress bar." },
  { icon: "📦", title: "Boxes & tables", description: "Rounded, double, heavy or ASCII boxes. Auto-sized tables with optional header colors." },
  { icon: "🎮", title: "Interactive menus", description: "Arrow-key navigation with ↑ ↓ Enter — feels like a real TUI, not a printf wall." },
  { icon: "🖱️", title: "Cursor control", description: "Hide, show, move, save, restore, jump to any (row, col). Tiny API, total control." },
];

const Index = () => {
  return (
    <div className="min-h-screen relative">
      {/* HEADER */}
      <header className="container mx-auto px-6 py-6 flex items-center justify-between">
        <div className="flex items-center gap-2 font-mono">
          <span className="text-primary text-xl">▶</span>
          <span className="font-bold text-lg">TerminalLib</span>
          <span className="text-xs text-muted-foreground ml-2 hidden sm:inline">v2.0</span>
        </div>
        <nav className="flex items-center gap-2 sm:gap-4">
          <a href="#features" className="text-sm text-muted-foreground hover:text-foreground transition-colors hidden sm:inline">Features</a>
          <a href="#install" className="text-sm text-muted-foreground hover:text-foreground transition-colors hidden sm:inline">Install</a>
          <Button asChild variant="outline" size="sm" className="font-mono">
            <a href="https://github.com/miguel-lamazares/TerminalLib" target="_blank" rel="noreferrer">
              GitHub →
            </a>
          </Button>
        </nav>
      </header>

      {/* HERO */}
      <section className="relative">
        <div className="absolute inset-0 grid-bg opacity-40 pointer-events-none" />
        <div className="container mx-auto px-6 pt-12 pb-20 md:pt-20 md:pb-28 relative">
          <div className="max-w-3xl mx-auto text-center mb-14 animate-fade-in-up">
            <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full border border-border bg-muted/30 text-xs font-mono text-muted-foreground mb-6">
              <span className="w-2 h-2 rounded-full bg-secondary animate-glow-pulse" />
              v2 · Python · Java · Ruby
            </div>
            <h1 className="text-4xl md:text-6xl lg:text-7xl font-bold tracking-tight mb-6 font-mono">
              Your terminal{" "}
              <span
                className="text-gradient-rainbow animate-rainbow-shift"
                style={{ backgroundSize: "300% 300%" }}
              >
                doesn't need
              </span>{" "}
              to be ugly.
            </h1>
            <p className="text-lg md:text-xl text-muted-foreground max-w-2xl mx-auto leading-relaxed">
              A tiny, dependency-free toolkit that brings colors, cursor magic,
              animations and interactive UI to plain old CLI apps.
            </p>
            <div className="flex flex-wrap items-center justify-center gap-3 mt-8">
              <Button asChild size="lg" className="font-mono bg-gradient-to-r from-primary to-accent text-primary-foreground hover:opacity-90 glow-primary">
                <a href="#install">Get started →</a>
              </Button>
              <Button asChild size="lg" variant="outline" className="font-mono">
                <a href="https://github.com/miguel-lamazares/TerminalLib" target="_blank" rel="noreferrer">
                  ★ Star on GitHub
                </a>
              </Button>
            </div>
          </div>

          <div className="max-w-3xl mx-auto animate-scale-in">
            <TerminalDemo />
          </div>
        </div>
      </section>

      {/* FEATURES */}
      <section id="features" className="container mx-auto px-6 py-20">
        <div className="text-center mb-12">
          <h2 className="text-3xl md:text-4xl font-bold font-mono mb-3">
            <span className="text-gradient-primary">Batteries</span> included.
          </h2>
          <p className="text-muted-foreground max-w-xl mx-auto">
            Everything you need to make a CLI app feel alive — without pulling in a heavy framework.
          </p>
        </div>
        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-5">
          {FEATURES.map((f) => (
            <FeatureCard key={f.title} {...f} />
          ))}
        </div>
      </section>

      {/* CODE */}
      <section className="container mx-auto px-6 py-20">
        <div className="grid lg:grid-cols-2 gap-10 items-center">
          <div>
            <h2 className="text-3xl md:text-4xl font-bold font-mono mb-4">
              Same vibe in <span className="text-gradient-primary">three languages</span>.
            </h2>
            <p className="text-muted-foreground mb-6 leading-relaxed">
              Python, Java and Ruby implementations expose the same mental model.
              Jump between stacks without retraining your fingers.
            </p>
            <ul className="space-y-2 font-mono text-sm">
              {["🐍 Python — pip install -e .", "☕ Java — JitPack dependency", "💎 Ruby — drop-in single file"].map((t) => (
                <li key={t} className="flex items-center gap-2 text-foreground/80">
                  <span className="text-primary">›</span>{t}
                </li>
              ))}
            </ul>
          </div>
          <CodeTabs />
        </div>
      </section>

      {/* INSTALL */}
      <section id="install" className="container mx-auto px-6 py-20">
        <div className="max-w-3xl mx-auto rounded-2xl border border-border bg-card/60 p-8 md:p-12 shadow-terminal">
          <h2 className="text-3xl font-bold font-mono mb-6 text-center">
            Install in 10 seconds.
          </h2>
          <div className="space-y-4 font-mono text-sm">
            <div className="rounded-lg bg-background/70 border border-border p-4">
              <div className="text-xs text-muted-foreground mb-1">🐍 Python</div>
              <code className="text-secondary">pip install -e .</code>
            </div>
            <div className="rounded-lg bg-background/70 border border-border p-4">
              <div className="text-xs text-muted-foreground mb-1">☕ Java (Maven + JitPack)</div>
              <code className="text-secondary whitespace-pre block">{`<dependency>
  <groupId>com.github.miguel-lamazares</groupId>
  <artifactId>TerminalLib</artifactId>
  <version>v2.0.0</version>
</dependency>`}</code>
            </div>
            <div className="rounded-lg bg-background/70 border border-border p-4">
              <div className="text-xs text-muted-foreground mb-1">💎 Ruby</div>
              <code className="text-secondary">require_relative 'Terminal'</code>
            </div>
          </div>
        </div>
      </section>

      {/* FOOTER */}
      <footer className="border-t border-border mt-10">
        <div className="container mx-auto px-6 py-8 flex flex-col sm:flex-row items-center justify-between gap-3 text-sm text-muted-foreground font-mono">
          <span>Made with ❤️ by Miguel Lamazares</span>
          <span className="text-gradient-primary">Because terminals deserve better.</span>
        </div>
      </footer>
    </div>
  );
};

export default Index;
