require "io/console"

# -------------------------------------------------
# UTILS
# -------------------------------------------------

def tty?
  STDOUT.tty?
end

def reset_style
  print "\e[0m"
  STDOUT.flush
end

# -------------------------------------------------
# CLEAR TERMINAL
# -------------------------------------------------

def clear_all
  return unless tty?
  print "\e[H\e[2J"
end

# -------------------------------------------------
# WAIT ENTER AND CLEAR
# -------------------------------------------------

def wait_enter_clear(prompt = "\n\nPress Enter...")
  print prompt
  STDIN.gets
  if tty?
    print "\e[F\e[K"
    STDOUT.flush
  end
end

# -------------------------------------------------
# COLORS
# -------------------------------------------------

module Colors
  RESET  = "\e[0m"
  BLACK  = "\e[30m"
  RED    = "\e[31m"
  GREEN  = "\e[32m"
  YELLOW = "\e[33m"
  BLUE   = "\e[34m"
  PURPLE = "\e[35m"
  CYAN   = "\e[36m"
  WHITE  = "\e[37m"
end

# -------------------------------------------------
# READ INTEGER INPUT
# -------------------------------------------------

def read_int(min, max)
  raise "read_int requires an interactive terminal" unless STDIN.tty?

  loop do
    input = STDIN.gets&.strip
    unless input&.match?(/^\d+$/)
      clear_last_line
      next
    end

    value = input.to_i
    if value < min || value > max
      clear_last_line
      next
    end

    return value
  end
end

def clear_last_line
  print "\e[F\e[K"
  STDOUT.flush
end

# -------------------------------------------------
# TYPEWRITE
# -------------------------------------------------

def typewrite(text, speed = 0.03)
  text.each_char do |c|
    print c
    STDOUT.flush
    sleep speed
  end
  puts
end

# -------------------------------------------------
# PROGRESS BAR
# -------------------------------------------------

def print_progress_bar(current, total, bar_length = 20)
  return unless tty?
  return if total <= 0

  progress = [current.to_f / total, 1.0].min
  filled = (bar_length * progress).to_i
  bar = "∎" * filled + "." * (bar_length - filled)
  percent = progress * 100

  print "\r[#{bar}] #{format('%.2f', percent)}% #{current}/#{total}"
  STDOUT.flush

  puts if current >= total
end

# -------------------------------------------------
# CENTERED TEXT
# -------------------------------------------------

def print_centered_text(text)
  unless tty?
    puts text
    return
  end

  width = IO.console.winsize[1]
  text.split("\n").each do |line|
    clean = line.gsub(/\e\[[0-9;]*[A-Za-z]/, "")
    padding = [(width - clean.length) / 2, 0].max
    puts " " * padding + line
  end
end

# -------------------------------------------------
# RAINBOW LOADING
# -------------------------------------------------

def rainbow_loading(text, max_dots, stop, speed = 0.3)
  return unless tty?

  colors = [
    Colors::RED,
    Colors::YELLOW,
    Colors::GREEN,
    Colors::CYAN,
    Colors::BLUE,
    Colors::PURPLE
  ]

  dots = 1
  color_index = 0

  begin
    until stop.call
      print "\r#{colors[color_index]}#{text} #{'.' * dots}#{Colors::RESET}"
      STDOUT.flush
      sleep speed

      dots += 1
      dots = 1 if dots > max_dots
      color_index = (color_index + 1) % colors.size
    end
  ensure
    reset_style
    puts
  end
end

# -------------------------------------------------
# SPINNER
# -------------------------------------------------

def spin_bar(stop, speed = 0.1)
  return unless tty?

  bars = ["|", "/", "-", "\\"]
  index = 0

  begin
    until stop.call
      print "\r#{bars[index]}"
      STDOUT.flush
      sleep speed
      index = (index + 1) % bars.size
    end
  ensure
    reset_style
    puts
  end
end
