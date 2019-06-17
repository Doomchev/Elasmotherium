number = Int.random(100)
do {
	Int guess = enterString("What number did I guess?")
	if(guess < number) {
		showMessage("Your number is too small!")
	} else if(guess > number) {
		showMessage("Your number is too big!")
	} else {
		showMessage("You are right!")
		end
	}
}

