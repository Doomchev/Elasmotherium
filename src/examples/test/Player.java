package examples.test;

public class Player {
  String firstName;
  String lastName;
  int age = 18;
  String description() {
    return this.firstName + " " + this.lastName + ", " + this.age + " years old";
  };
  Player(String firstName, String lastName, int age) {
    this.age = age;
    this.lastName = lastName;
    this.firstName = firstName;
  }
  void incrementAge() {
    this.age++;
  }
  void incrementAge(int value) {
    this.age += value;
  }
}