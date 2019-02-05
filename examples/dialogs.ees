myName = "Cyrus"
yourName = enterString("What is your name?")

Option {
	String caption
	abstract execute()
}

optionGreet = Option {
	caption: "Greet"
	execute() {
		showMessage("\(yourName): Hello, \(myName)!\n\(myName): Hello, \(yourName)!")
	}
}

List<String> fruits = ["apples", "peaches", "oranges"]

optionAsk = Option {
	caption: "Ask"
	execute() {
		fruit = selectOption("Question", "Select fruit you want to ask about:", fruits, 1)
		if(fruit >= 0) showMessage("\(yourName): Do you like \(fruits[fruit])?\n"
				+ "\(myName): Yes, I like to eat \(fruits[fruit]).")
	}
}

optionExit = Option {
	caption: "Exit"
	execute() {
		end
	}
}

List<Option> options = [optionGreet, optionAsk, optionExit]
do {
	option = selectOption("Options", "Choose what to do:", options, 0)
	if(option < 0) end
	options[option].execute()
}