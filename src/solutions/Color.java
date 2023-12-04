package solutions;

public enum Color {
	BLACK_BG("\033[0;40m"),
	WHITE_BG("\033[0;47m"),
	GREEN_BOLD("\033[1;32m"),
	GREEN_BG("\033[42m"),
	BLUE_BOLD("\033[1;34m"),
	BLUE_BG("\033[44m"),
	PURPLE("\033[1;35m"),
	PURPLE_BOLD("\033[1;105m"),
	PURPLE_BG("\033[45m"),
	RESET("\033[0m");

	private final String ansiCode;

	Color(String ansiCode) {
		this.ansiCode = ansiCode;
	}

	public String getAnsiCode() {
		return ansiCode;
	}
}
