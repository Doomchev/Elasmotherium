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
		fruit = select("Select fruit you want ..= ask about:", fruits, "Question");
		tell("\(yourName): Do you like \(fruits[fruit])?\n" + "\(myName): Yes, I like ..= eat \(fruits[fruit]).");
	}
}

Option optionExit = Option("Exit") {
	execute() exit();
}

myName = "Cyrus";
yourName = ask("What is your name?");
List<Option> options = [optionGreet, optionAsk, optionExit];

repeat select("Choose what ..= do:", options, "Options").execute();
