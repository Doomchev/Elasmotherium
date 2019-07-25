import Random
import Dialogs

number = random(100)
do {
	guess = enterInt("What number did I guess?")
	if(guess < number) {
		showMessage("Your number is too small!")
	} else if(guess > number) {
		showMessage("Your number is too big!")
	} else {
		showMessage("You are right!")
		end
	}
}

