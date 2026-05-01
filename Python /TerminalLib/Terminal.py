import sys
import time
import os
import requests
import shutil
import re
import random
import math
import threading
import itertools

# ╔══════════════════════════════════════════════════════════════╗
# ║                    TerminalLib · Python                      ║
# ║   Your terminal doesn't need to be ugly.                     ║
# ╚══════════════════════════════════════════════════════════════╝

# ============================================================
# ORIGINAL API  (kept exactly as written — never deleted)
# ============================================================

#
# CLEAR TERMINAL
#

def Clear_all():
    print("\033[H\033[2J", flush=True)

#
# WAIT ENTER AND CLEAR
#

def wait_enter_clear(prompt="\n\nPress Enter..."):
    input(prompt)
    print("\033[F\033[K", end="")

#
# COLORS
#

class Colors():
    RESET = "\u001B[0m";
    BLACK = "\u001B[30m";
    RED = "\u001B[31m";
    GREEN = "\u001B[32m";
    YELLOW = "\u001B[33m";
    BLUE = "\u001B[34m";
    PURPLE = "\u001B[35m";
    CYAN = "\u001B[36m";
    WHITE = "\u001B[37m";

#
# READ RESPONSE FROM USER
# 

def read_int(Min_value: int, max_value: int) -> int:
    while True:
        user_input = input()

        if not user_input.strip().isdigit():
            sys.stdout.write("\033[F")
            sys.stdout.write("\033[K")
            continue

        choice = int(user_input)

        if choice <= Min_value - 1:
            sys.stdout.write("\033[F")
            sys.stdout.write("\033[K")
            continue

        if choice >= max_value + 1:
            sys.stdout.write("\033[F")
            sys.stdout.write("\033[K")
            continue

        return choice
    

#    
# TYPEWRITE
#
     
def typewrite(text, speed=0.03):
    for char in text:
        sys.stdout.write(char)
        sys.stdout.flush()
        time.sleep(speed)
    print()  


# 
# BAR
#

def print_progress_bar(current, total, bar_length=20):

    progress = current / total
    filled_length = int(bar_length * progress)
    bar = '∎' * filled_length + '.' * (bar_length - filled_length)
    por = current / total * 100;
    sys.stdout.write(f'\r[{bar}] {por:.2f}% {current}/{total} :) ')
    sys.stdout.flush()
    if current == total:
        print()

#
# PRINT CENTRALIZED TEXT
#

def get_terminal_width():
    return os.get_terminal_size().columns

def strip_ansi_codes(text):
    ansi_escape = re.compile(r'\x1B\[[0-9;]*[A-Za-z]')
    return ansi_escape.sub('', text)

def print_centralizedText(text):
    terminal_width = get_terminal_width()
    lines = text.split('\n')
    
    for line in lines:

        clean_line = strip_ansi_codes(line)
        line_length = len(clean_line)

        padding = max(0, (terminal_width - line_length) // 2)
 
        print(' ' * padding + line)


# ╔══════════════════════════════════════════════════════════════╗
# ║   ✦ EXTENDED API · v2  (new features, originals untouched)   ║
# ╚══════════════════════════════════════════════════════════════╝

#
# CURSOR CONTROL
#

class Cursor:
    HIDE      = "\033[?25l"
    SHOW      = "\033[?25h"
    SAVE      = "\033[s"
    RESTORE   = "\033[u"

    @staticmethod
    def up(n=1):    sys.stdout.write(f"\033[{n}A"); sys.stdout.flush()
    @staticmethod
    def down(n=1):  sys.stdout.write(f"\033[{n}B"); sys.stdout.flush()
    @staticmethod
    def right(n=1): sys.stdout.write(f"\033[{n}C"); sys.stdout.flush()
    @staticmethod
    def left(n=1):  sys.stdout.write(f"\033[{n}D"); sys.stdout.flush()
    @staticmethod
    def goto(row, col): sys.stdout.write(f"\033[{row};{col}H"); sys.stdout.flush()
    @staticmethod
    def clear_line(): sys.stdout.write("\033[2K\r"); sys.stdout.flush()
    @staticmethod
    def hide():     sys.stdout.write(Cursor.HIDE); sys.stdout.flush()
    @staticmethod
    def show():     sys.stdout.write(Cursor.SHOW); sys.stdout.flush()


#
# TEXT STYLES (bold, italic, underline, blink, reverse, strike)
#

class Style:
    RESET     = "\033[0m"
    BOLD      = "\033[1m"
    DIM       = "\033[2m"
    ITALIC    = "\033[3m"
    UNDERLINE = "\033[4m"
    BLINK     = "\033[5m"
    REVERSE   = "\033[7m"
    HIDDEN    = "\033[8m"
    STRIKE    = "\033[9m"


#
# 24-BIT RGB & 256-COLOR SUPPORT
#

class RGB:
    @staticmethod
    def fg(r, g, b):  return f"\033[38;2;{r};{g};{b}m"
    @staticmethod
    def bg(r, g, b):  return f"\033[48;2;{r};{g};{b}m"
    @staticmethod
    def fg256(n):     return f"\033[38;5;{n}m"
    @staticmethod
    def bg256(n):     return f"\033[48;5;{n}m"

    @staticmethod
    def hex(hex_str):
        h = hex_str.lstrip('#')
        return RGB.fg(int(h[0:2], 16), int(h[2:4], 16), int(h[4:6], 16))


#
# GRADIENT TEXT
#

def gradient_text(text, start_rgb=(255, 0, 128), end_rgb=(0, 200, 255)):
    """Render text with a smooth RGB gradient between two colors."""
    out = ""
    n = max(len(text) - 1, 1)
    for i, ch in enumerate(text):
        t = i / n
        r = int(start_rgb[0] + (end_rgb[0] - start_rgb[0]) * t)
        g = int(start_rgb[1] + (end_rgb[1] - start_rgb[1]) * t)
        b = int(start_rgb[2] + (end_rgb[2] - start_rgb[2]) * t)
        out += f"{RGB.fg(r, g, b)}{ch}"
    return out + Style.RESET


def rainbow_text(text):
    """Classic rainbow text using HSV → RGB."""
    out = ""
    for i, ch in enumerate(text):
        h = (i / max(len(text), 1)) * 360
        r, g, b = _hsv_to_rgb(h, 1, 1)
        out += f"{RGB.fg(r, g, b)}{ch}"
    return out + Style.RESET


def _hsv_to_rgb(h, s, v):
    c = v * s
    x = c * (1 - abs((h / 60) % 2 - 1))
    m = v - c
    if   0 <= h < 60:   r, g, b = c, x, 0
    elif 60 <= h < 120: r, g, b = x, c, 0
    elif 120 <= h < 180:r, g, b = 0, c, x
    elif 180 <= h < 240:r, g, b = 0, x, c
    elif 240 <= h < 300:r, g, b = x, 0, c
    else:               r, g, b = c, 0, x
    return int((r + m) * 255), int((g + m) * 255), int((b + m) * 255)


#
# ADVANCED TEXT EFFECTS
#

def glitch(text, duration=1.5, intensity=0.3):
    """Glitch effect — randomly swaps characters before settling on the real text."""
    glitch_chars = "!@#$%^&*()_+-=[]{}|;:,.<>?/~`"
    end = time.time() + duration
    Cursor.hide()
    while time.time() < end:
        out = "".join(random.choice(glitch_chars) if random.random() < intensity else c for c in text)
        sys.stdout.write("\r" + RGB.fg(0, 255, 120) + out + Style.RESET)
        sys.stdout.flush()
        time.sleep(0.05)
    sys.stdout.write("\r" + text + " " * 4 + "\n")
    Cursor.show()


def shake(text, duration=1.0):
    """Horizontal shake effect."""
    end = time.time() + duration
    Cursor.hide()
    while time.time() < end:
        offset = random.randint(0, 4)
        sys.stdout.write("\r" + " " * offset + text + " " * (4 - offset))
        sys.stdout.flush()
        time.sleep(0.04)
    sys.stdout.write("\r" + text + "    \n")
    Cursor.show()


def wave(text, cycles=2, speed=0.05):
    """Sine-wave coloring effect that animates across the text."""
    Cursor.hide()
    for frame in range(int(cycles * 30)):
        out = ""
        for i, ch in enumerate(text):
            phase = (i + frame) * 0.3
            r = int(127 + 127 * math.sin(phase))
            g = int(127 + 127 * math.sin(phase + 2))
            b = int(127 + 127 * math.sin(phase + 4))
            out += f"{RGB.fg(r, g, b)}{ch}"
        sys.stdout.write("\r" + out + Style.RESET)
        sys.stdout.flush()
        time.sleep(speed)
    print()
    Cursor.show()


def fade_in(text, speed=0.04):
    """Fade text in character by character with brightness ramp."""
    Cursor.hide()
    for i in range(1, len(text) + 1):
        sys.stdout.write("\r" + text[:i])
        sys.stdout.flush()
        time.sleep(speed)
    print()
    Cursor.show()


def matrix_rain(duration=3.0, width=None, density=0.02):
    """Cinematic Matrix-style falling characters."""
    if width is None:
        width = get_terminal_width()
    height = shutil.get_terminal_size().lines - 2
    drops = [0] * width
    chars = "ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄ01"
    Cursor.hide()
    Clear_all()
    end = time.time() + duration
    try:
        while time.time() < end:
            for i in range(width):
                if random.random() < density or drops[i] > 0:
                    drops[i] = (drops[i] + 1) % height
                    Cursor.goto(drops[i] + 1, i + 1)
                    sys.stdout.write(RGB.fg(0, 255, 80) + random.choice(chars))
                    Cursor.goto(max(drops[i] - 4, 1), i + 1)
                    sys.stdout.write(" ")
            sys.stdout.flush()
            time.sleep(0.05)
    finally:
        sys.stdout.write(Style.RESET)
        Cursor.show()
        Clear_all()


#
# SPINNERS  (run in background thread)
#

SPINNER_FRAMES = {
    "dots":     ["⠋", "⠙", "⠹", "⠸", "⠼", "⠴", "⠦", "⠧", "⠇", "⠏"],
    "line":     ["-", "\\", "|", "/"],
    "arrows":   ["←", "↖", "↑", "↗", "→", "↘", "↓", "↙"],
    "moon":     ["🌑","🌒","🌓","🌔","🌕","🌖","🌗","🌘"],
    "bouncing": ["[    ]","[=   ]","[==  ]","[=== ]","[ ===]","[  ==]","[   =]","[    ]"],
    "pulse":    ["•", "●", "◉", "●", "•"],
}


class Spinner:
    def __init__(self, message="Loading", style="dots", color=None):
        self.frames = SPINNER_FRAMES.get(style, SPINNER_FRAMES["dots"])
        self.message = message
        self.color = color or RGB.fg(0, 200, 255)
        self._stop = threading.Event()
        self._thread = None

    def _run(self):
        Cursor.hide()
        for frame in itertools.cycle(self.frames):
            if self._stop.is_set():
                break
            sys.stdout.write(f"\r{self.color}{frame}{Style.RESET} {self.message}")
            sys.stdout.flush()
            time.sleep(0.08)
        Cursor.clear_line()
        Cursor.show()

    def start(self):
        self._stop.clear()
        self._thread = threading.Thread(target=self._run, daemon=True)
        self._thread.start()
        return self

    def stop(self, final_message=None):
        self._stop.set()
        if self._thread:
            self._thread.join()
        if final_message:
            print(final_message)

    def __enter__(self): return self.start()
    def __exit__(self, *a): self.stop()


#
# BOXES & BORDERS
#

BOX_STYLES = {
    "single":  "┌─┐│└─┘│",
    "double":  "╔═╗║╚═╝║",
    "rounded": "╭─╮│╰─╯│",
    "heavy":   "┏━┓┃┗━┛┃",
    "ascii":   "+-+|+-+|",
}


def box(text, style="rounded", padding=1, color=None):
    """Wrap multiline text in a decorative box."""
    s = BOX_STYLES.get(style, BOX_STYLES["rounded"])
    tl, h, tr, r, bl, _, br, l = s
    lines = text.split("\n")
    width = max(len(strip_ansi_codes(ln)) for ln in lines)
    pad = " " * padding
    color = color or ""
    rst = Style.RESET if color else ""

    top    = f"{color}{tl}{h * (width + padding * 2)}{tr}{rst}"
    bottom = f"{color}{bl}{h * (width + padding * 2)}{br}{rst}"
    body = []
    for ln in lines:
        clean_len = len(strip_ansi_codes(ln))
        body.append(f"{color}{l}{rst}{pad}{ln}{' ' * (width - clean_len)}{pad}{color}{r}{rst}")
    return "\n".join([top, *body, bottom])


def hr(char="─", color=None):
    """Horizontal rule across the terminal."""
    w = get_terminal_width()
    line = char * w
    return f"{color}{line}{Style.RESET}" if color else line


#
# TABLE
#

def table(headers, rows, color_header=None):
    """Pretty ASCII table with auto column widths."""
    cols = len(headers)
    widths = [len(str(h)) for h in headers]
    for row in rows:
        for i in range(cols):
            widths[i] = max(widths[i], len(str(row[i])))

    def fmt_row(row):
        return "│ " + " │ ".join(str(row[i]).ljust(widths[i]) for i in range(cols)) + " │"

    sep_top = "┌─" + "─┬─".join("─" * w for w in widths) + "─┐"
    sep_mid = "├─" + "─┼─".join("─" * w for w in widths) + "─┤"
    sep_bot = "└─" + "─┴─".join("─" * w for w in widths) + "─┘"

    header_line = fmt_row(headers)
    if color_header:
        header_line = color_header + header_line + Style.RESET

    return "\n".join([sep_top, header_line, sep_mid, *(fmt_row(r) for r in rows), sep_bot])


#
# INTERACTIVE MENU (arrow-key navigation, cross-platform)
#

def menu(title, options, color=None):
    """Render an interactive menu navigated with ↑/↓ + Enter."""
    color = color or RGB.fg(0, 220, 255)
    selected = 0

    def _getch():
        try:
            import termios, tty
            fd = sys.stdin.fileno()
            old = termios.tcgetattr(fd)
            try:
                tty.setraw(fd)
                ch = sys.stdin.read(1)
                if ch == "\x1b":
                    ch += sys.stdin.read(2)
            finally:
                termios.tcsetattr(fd, termios.TCSADRAIN, old)
            return ch
        except Exception:
            import msvcrt
            ch = msvcrt.getch()
            if ch in (b"\x00", b"\xe0"):
                ch += msvcrt.getch()
            return ch.decode("utf-8", errors="ignore")

    Cursor.hide()
    try:
        while True:
            sys.stdout.write("\r")
            print(f"{Style.BOLD}{color}{title}{Style.RESET}")
            for i, opt in enumerate(options):
                marker = "❯" if i == selected else " "
                line = f" {marker} {opt}"
                if i == selected:
                    line = f"{color}{Style.BOLD}{line}{Style.RESET}"
                print(line)
            key = _getch()
            if key in ("\x1b[A", "\x00H", "\xe0H"):  # up
                selected = (selected - 1) % len(options)
            elif key in ("\x1b[B", "\x00P", "\xe0P"):  # down
                selected = (selected + 1) % len(options)
            elif key in ("\r", "\n"):
                return selected
            # rewind
            Cursor.up(len(options) + 1)
            for _ in range(len(options) + 1):
                Cursor.clear_line()
                Cursor.down(1)
            Cursor.up(len(options) + 1)
    finally:
        Cursor.show()


#
# BEEP
#

def beep():
    """Terminal bell."""
    sys.stdout.write("\a")
    sys.stdout.flush()


#
#   BLEND TEXT
#

def blend_text(text, colors):
    """
    Render text blending through multiple RGB colors.

    Example:
        print(blend_text("TerminalLib", [(255, 0, 0), (0, 255, 0), (0, 0, 255)]))

    Also accepts hex:
        print(blend_text("TerminalLib", ["#ff0000", "#00ff00", "#0000ff"]))
    """

    if not text:
        return ""

    if not colors or len(colors) < 2:
        raise ValueError("colors must contain at least two colors")

    PRESET_COLORS = {
    "black": (0, 0, 0),
    "red": (255, 0, 0),
    "green": (0, 255, 0),
    "yellow": (255, 255, 0),
    "blue": (0, 0, 255),
    "purple": (180, 0, 255),
    "cyan": (0, 255, 255),
    "white": (255, 255, 255),
}

    def normalize_color(c):
        # HEX
        if isinstance(c, str) and c.startswith("#"):
            h = c.lstrip("#")
            return (
                int(h[0:2], 16),
                int(h[2:4], 16),
                int(h[4:6], 16),
            )

        # PRESET (string tipo "red")
        if isinstance(c, str):
            if c.lower() in PRESET_COLORS:
                return PRESET_COLORS[c.lower()]
            raise ValueError(f"Unknown color preset: {c}")

        # RGB tuple
        if isinstance(c, tuple) and len(c) == 3:
            return c

        raise ValueError(f"Invalid color: {c}")

    colors = [normalize_color(c) for c in colors]

    out = ""
    n = max(len(text) - 1, 1)
    segments = len(colors) - 1

    for i, ch in enumerate(text):
        t = i / n
        segment = min(int(t * segments), segments - 1)
        local_t = (t * segments) - segment

        c1 = colors[segment]
        c2 = colors[segment + 1]

        r = int(c1[0] + (c2[0] - c1[0]) * local_t)
        g = int(c1[1] + (c2[1] - c1[1]) * local_t)
        b = int(c1[2] + (c2[2] - c1[2]) * local_t)

        out += f"{RGB.fg(r, g, b)}{ch}"

    return out + Style.RESET



#
# DEMO
#

def _demo():
    Clear_all()
    print(rainbow_text("✦ TerminalLib · Python · v2 ✦"))
    print(hr("─", RGB.fg(80, 80, 80)))
    print(box("Your terminal\ndoesn't need to be ugly.", style="rounded", color=RGB.fg(0, 220, 255)))
    typewrite(gradient_text("Loading awesome features..."))
    with Spinner("Crunching numbers", style="dots"):
        time.sleep(1.5)
    glitch("SYSTEM ONLINE", duration=1.2)
    wave("~ have fun ~", cycles=2)


if __name__ == "__main__":
    _demo()
