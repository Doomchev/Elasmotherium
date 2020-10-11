Player {
	($firstName, $lastName, $age) {}
	String firstName, lastName
	I64 age
}

Player player = Player("John", "Smith", 36)
print("\(player.firstName) \(player.lastName) is \(player.age) years old")