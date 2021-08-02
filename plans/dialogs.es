class Option {
	String caption;
	this(this.caption);
	execute();
}

Option optionGreet = Option("Greet") {
	execute() tell("\(yourName): Hello, \(myName)!\n\(myName): Hello, \(yourName)!");
}

List<String> fruits = ["apples", "peaches", "oranges"]

optionAsk = Option("Ask") {
	execute() {
		try {
			String fruit = select("Question", "Select fruit you want to ask about:", fruits, 1);
			tell("\(yourName): Do you like \(fruits[fruit])?\n" + "\(myName): Yes, I like to eat \(fruits[fruit]).");
		}
	}
}

optionExit = Option("Exit") {
	execute() exit();
}

try {
	String myName = "Cyrus";
	String yourName = ask("What is your name?");
	List<Option> options = [optionGreet, optionAsk, optionExit];

	do {
		Int option = select("Options", "Choose what to do:", options, 0);
		options[option].execute()
	}
}