String myName = "Cyrus";
String yourName = ask("What is your name?");

class Option {
	String caption;
	
	this(this.caption);
	
	execute();
}

Option optionGreet = new Option("Greet") {
	execute()
		tell("\(yourName): Hello, \(myName)!\n\(myName): Hello, \(yourName)!");
}

List<String> fruits = ["apples", "peaches", "oranges"]

optionAsk = new Option("Ask") {
	execute() {
		String fruit = select("Question", "Select fruit you want to ask about:", fruits, 1);
		if(fruit >= 0) tell("\(yourName): Do you like \(fruits[fruit])?\n"
				+ "\(myName): Yes, I like to eat \(fruits[fruit]).");
	}
}

optionExit = new Option("Exit") {
	execute()
		exit();
}

List<Option> options = [optionGreet, optionAsk, optionExit];
do {
	Int option = selectOption("Options", "Choose what to do:", options, 0);
	if(option < 0) exit();
	options[option].execute()
}