import sys
import time
import os
import re

# -------------------------------------------------
# UTILS
# -------------------------------------------------

def is_tty():
    return sys.stdout.isatty()


def reset_style():
    sys.stdout.write("\033[0m")
    sys.stdout.flush()


# -------------------------------------------------
# CLEAR TERMINAL
# -------------------------------------------------

def clear_all():
    if not is_tty():
        return
    print("\033[H\033[2J", flush=True)


# -------------------------------------------------
# WAIT ENTER AND CLEAR
# -------------------------------------------------

def wait_enter_clear(prompt="\n\nPress Enter..."):
    input(prompt)
    if is_tty():
        sys.stdout.write("\033[F\033[K")
        sys.stdout.flush()


# -------------------------------------------------
# COLORS
# -------------------------------------------------

class Colors:
    RESET = "\033[0m"
    BLACK = "\033[30m"
    RED = "\033[31m"
    GREEN = "\033[32m"
    YELLOW = "\033[33m"
    BLUE = "\033[34m"
    PURPLE = "\033[35m"
    CYAN = "\033[36m"
    WHITE = "\033[37m"


# -------------------------------------------------
# READ INTEGER INPUT
# -------------------------------------------------

def read_int(min_value: int, max_value: int) -> int:
    if not sys.stdin.isatty():
        raise RuntimeError("read_int requires an interactive terminal")

    while True:
        user_input = input()

        if not user_input.strip().isdigit():
            _clear_last_line()
            continue

        choice = int(user_input)

        if choice < min_value or choice > max_value:
            _clear_last_line()
            continue

        return choice


def _clear_last_line():
    sys.stdout.write("\033[F\033[K")
    sys.stdout.flush()


# -------------------------------------------------
# TYPEWRITE
# -------------------------------------------------

def typewrite(text, speed=0.03):
    for char in text:
        sys.stdout.write(char)
        sys.stdout.flush()
        time.sleep(speed)
    print()


# -------------------------------------------------
# PROGRESS BAR
# -------------------------------------------------

def print_progress_bar(current, total, bar_length=20):
    if not is_tty() or total <= 0:
        return

    progress = min(current / total, 1)
    filled_length = int(bar_length * progress)
    bar = "∎" * filled_length + "." * (bar_length - filled_length)
    percent = progress * 100

    sys.stdout.write(f"\r[{bar}] {percent:.2f}% {current}/{total}")
    sys.stdout.flush()

    if current >= total:
        print()


# -------------------------------------------------
# CENTERED TEXT
# -------------------------------------------------

def get_terminal_width():
    return os.get_terminal_size().columns


def strip_ansi_codes(text):
    ansi_escape = re.compile(r"\x1B\[[0-9;]*[A-Za-z]")
    return ansi_escape.sub("", text)


def print_centered_text(text):
    if not is_tty():
        print(text)
        return

    terminal_width = get_terminal_width()
    lines = text.split("\n")

    for line in lines:
        clean_line = strip_ansi_codes(line)
        padding = max(0, (terminal_width - len(clean_line)) // 2)
        print(" " * padding + line)


# -------------------------------------------------
# RAINBOW LOADING
# -------------------------------------------------

def rainbow_loading(text, max_dots, stop, speed=0.3):
    if not is_tty():
        return

    colors = [
        Colors.RED,
        Colors.YELLOW,
        Colors.GREEN,
        Colors.CYAN,
        Colors.BLUE,
        Colors.PURPLE,
    ]

    current_length = 1
    color_index = 0

    try:
        while not stop():
            dots = "." * current_length
            color = colors[color_index]

            sys.stdout.write(
                "\r" + color + text + " " + dots + Colors.RESET
            )
            sys.stdout.flush()

            time.sleep(speed)

            current_length += 1
            if current_length > max_dots:
                current_length = 1

            color_index = (color_index + 1) % len(colors)
    finally:
        reset_style()
        print()


# -------------------------------------------------
# SPINNER
# -------------------------------------------------

def spin_bar(stop, speed=0.1):
    if not is_tty():
        return

    bars = ["|", "/", "-", "\\"]
    index = 0

    try:
        while not stop():
            sys.stdout.write("\r" + bars[index])
            sys.stdout.flush()
            time.sleep(speed)
            index = (index + 1) % len(bars)
    finally:
        reset_style()
        print()
