Int number = randomInt(100);
repeat {
	Int guess = askInt("What number did I guess?");
	if(guess < number) {
		say("Your number is too small!");
	} else if(guess > number) {
		say("Your number is too big!");
	} else {
		say("You are right!");
		exit();
	}
}