Player {
	($firstName, $lastName, $age) {}
	String firstName, lastName
	age = 18
	incrementAge() {
		$age++
	}
	get description -> "\($firstName) \($lastName), \($age) years old"
}

description = "looking around"
player = Player("John", "Smith", 36)
player.incrementAge()
print(player.description)

//List<String> list = ["one", "two", "three"]
//print(list.first())