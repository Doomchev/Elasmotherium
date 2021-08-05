class Option {
	String caption;
	create(this.caption);
	execute();
}

Option optionGreet = Option("Greet") {
	execute() tell("\(yourName): Hello, \(myName)!\n\(myName): Hello, \(yourName)!");
}

List<String> fruits = ["apples", "peaches", "oranges"];

Option optionAsk = Option("Ask") {
	execute() {
		String fruit = select("Select fruit you want to ask about:", fruits, "Question");
		tell("\(yourName): Do you like \(fruits[fruit])?\n" + "\(myName): Yes, I like to eat \(fruits[fruit]).");
	}
}

Option optionExit = Option("Exit") {
	execute() exit();
}

String myName = "Cyrus";
String yourName = ask("What is your name?");
List<Option> options = [optionGreet, optionAsk, optionExit];

do {
	Int option = select("Choose what to do:", options, "Options");
	options[option].execute()
}