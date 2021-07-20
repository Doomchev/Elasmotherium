Int number = random(100);
do {
	Int guess = askInteger("What number did I guess?");
	if(guess < number) {
		tell("Your number is too small!");
	} else if(guess > number) {
		tell("Your number is too big!");
	} else {
		tell("You are right!");
		exit();
	}
}