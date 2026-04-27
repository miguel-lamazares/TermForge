<div align="center">

# ✦ TerminalLib ✦

### *Your terminal doesn't need to be ugly.*

A tiny, dependency-free library that brings **colors, cursor magic, animations and interactive UI** to plain old CLI apps — in **Python 🐍**, **Java ☕** and **Ruby 💎**.

![status](https://img.shields.io/badge/status-active-brightgreen?style=flat-square)
![languages](https://img.shields.io/badge/languages-Python%20%7C%20Java%20%7C%20Ruby-blue?style=flat-square)
![license](https://img.shields.io/badge/license-MIT-lightgrey?style=flat-square)
![version](https://img.shields.io/badge/version-2.0-ff69b4?style=flat-square)

</div>

---

## 🎯 What is it?

**TerminalLib** is a small, friendly toolkit for building CLI applications that *don't look like they were made in 1985*. It gives you a curated set of helpers for:

- 🎨 **Colors** — basic ANSI, 256-color and full **24-bit RGB / hex / gradients / rainbow**
- 🖱️ **Cursor control** — hide, show, move, save, restore, jump anywhere
- ✍️ **Text effects** — typewriter, fade-in, glitch, shake, wave, blink, bold, italic, underline, strike
- ⏳ **Loaders** — multi-style spinners (`dots`, `line`, `arrows`, `moon`, `bouncing`, `pulse`) + progress bar
- 📦 **Layout** — boxes (rounded, double, heavy, ASCII), horizontal rules, ASCII tables, centered text
- 🎮 **Interactive menus** — arrow-key navigation with `↑ ↓ Enter`
- 🔔 **Quality of life** — input validation, terminal clearing, ANSI stripping, beep

All three implementations expose **the same mental model**, so jumping between languages feels natural.

---

## 📦 Install

### 🐍 Python

\`\`\`bash
git clone https://github.com/miguel-lamazares/TerminalLib.git
cd TerminalLib
pip install -e .
\`\`\`

…or just drop `Terminal.py` into your project.

### ☕ Java — via JitPack

\`\`\`xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependency>
  <groupId>com.github.miguel-lamazares</groupId>
  <artifactId>TerminalLib</artifactId>
  <version>v2.0.0</version>
</dependency>
\`\`\`

### 💎 Ruby

\`\`\`bash
git clone https://github.com/miguel-lamazares/TerminalLib.git
# then in your script:
require_relative 'Terminal'
\`\`\`

---

## ⚡ Quick taste

### Python

\`\`\`python
from Terminal import (
    Clear_all, typewrite, rainbow_text, gradient_text,
    glitch, wave, box, Spinner, RGB, menu
)

Clear_all()
print(rainbow_text("Welcome to my CLI!"))
print(box("Choose wisely.", style="rounded", color=RGB.fg(0, 220, 255)))

with Spinner("Loading universe", style="moon"):
    do_heavy_work()

choice = menu("Pick one:", ["New game", "Continue", "Quit"])
glitch("LET'S GO", duration=1.0)
\`\`\`

### Java

\`\`\`java
TerminalLib.clearAll();
System.out.println(TerminalLib.rainbowText("Welcome!"));
System.out.println(TerminalLib.box("Choose wisely.", "rounded", 1,
        TerminalLib.RGB.fg(0, 220, 255)));

TerminalLib.Spinner sp = new TerminalLib.Spinner(
        "Loading", TerminalLib.Spinner.MOON, null).start();
heavyWork();
sp.stop();

TerminalLib.glitch("LET'S GO", 1000, 0.3);
\`\`\`

### Ruby

\`\`\`ruby
require_relative 'Terminal'

clear_all
puts rainbow_text("Welcome!")
puts box("Choose wisely.", style: :rounded, color: RGB.fg(0, 220, 255))

Spinner.run(message: "Loading", style: :moon) { heavy_work }
glitch("LET'S GO", duration: 1.0)
\`\`\`

---

## 🧰 Feature matrix

|                          | Python 🐍 | Java ☕ | Ruby 💎 |
| ------------------------ | :-------: | :----: | :----: |
| Basic ANSI colors        |     ✅    |   ✅   |   ✅   |
| 256-color & 24-bit RGB   |     ✅    |   ✅   |   ✅   |
| Hex → ANSI               |     ✅    |   ✅   |   ✅   |
| Gradient & rainbow text  |     ✅    |   ✅   |   ✅   |
| Cursor control           |     ✅    |   ✅   |   ✅   |
| Typewriter               |     ✅    |   ✅   |   ✅   |
| Glitch / shake / wave / fade-in |  ✅ |   ✅   |   ✅   |
| Matrix rain              |     ✅    |   —    |   —    |
| Spinners (6 styles)      |     ✅    |   ✅   |   ✅   |
| Progress bar             |     ✅    |   ✅   |   ✅   |
| Boxes & borders          |     ✅    |   ✅   |   ✅   |
| Tables                   |     ✅    |   ✅   |   ✅   |
| Centered text            |     ✅    |   ✅   |   ✅   |
| Validated input          |     ✅    |   ✅   |   ✅   |
| Interactive arrow menus  |     ✅    |   —    |   ✅   |
| Beep                     |     ✅    |   ✅   |   ✅   |

---

## 🧪 Run the demos

\`\`\`bash
python  Terminal.py
ruby    Terminal.rb
javac   TerminalLib.java && java TerminalLib
\`\`\`

---

## 🛠️ Compatibility

- ✅ macOS Terminal / iTerm2
- ✅ Linux (gnome-terminal, kitty, alacritty, wezterm, …)
- ✅ Windows Terminal, PowerShell 7+, modern VSCode terminal
- ⚠️ Old `cmd.exe` may not render 24-bit color or unicode glyphs

---

## 🤝 Contributing

PRs, ideas and issues welcome — especially new spinner styles, box themes and effects.
**Rule of the house:** existing API never gets removed, only extended.

---

<div align="center">

Made with ❤️ by **Miguel Lamazares**

*Because terminals deserve better.*

</div>
