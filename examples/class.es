class Player {
	String firstName, lastName;
	Int age;
	
	this(this.firstName, this.lastName, this.age);
  
  incrementAge() age++;
	
	String description() -> "\(firstName) \(lastName) is \(age) years old";
}

Player player = Player("John", "Smith", 36);
player.incrementAge();
println(player.description());