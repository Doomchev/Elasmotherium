ask(String message, Function<Bool, Char> charFunction = null, Function<Bool, String> stringFunction = null) {
	
}
askInteger(String message) -> ask(message, (Char c) -> c >= '0' && c <= '9');
askDecimal(String message) -> ask(message, (Char c) -> c == '.' || c >= '0' && c <= '9', (String string) -> string.count('.') <= 1);