class Player {
	String firstName, lastName;
	Int age;
	
	create(field.firstName, field.lastName, field.age);
  
  incrementAge() age++;
  
  addToAge(Int value) {
    Int newAge = age + value;
    age = newAge;
  }
	
	String description() -> "\(firstName) \(lastName) is \(age) years old";
}

Player player = Player("John", "Smith", 36);
player.incrementAge();
player.addToAge(10);
println(player.description);