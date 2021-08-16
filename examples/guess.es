Int number = randomInt(100);
do {
	Int guess = askInt("What number did I guess?");
	if(guess < number) {
		tell("Your number is too small!");
	} else if(guess > number) {
		tell("Your number is too big!");
	} else {
		tell("You are right!");
		exit();
	}
}