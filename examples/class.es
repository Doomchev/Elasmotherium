Player {
	String firstName, lastName
	I64 age
}

Player player = Player()
player.firstName = "John"
player.lastName = "Smith"
player.age = 36
print("\(player.firstName) \(player.lastName) is \(player.age) years old")