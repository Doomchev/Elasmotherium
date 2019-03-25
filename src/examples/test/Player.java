package examples.test;
import java.util.LinkedList;
class Player {
  Player(String firstName, String lastName, long age) {
    this.age = age;
    this.lastName = lastName;
    this.firstName = firstName;
  }
  String firstName;
  String lastName;
  long age = 18;
  void incrementAge() {
     this.age++;
  }
  String description() {
    return this.firstName + " " + this.lastName + ", " + this.age + " years old";
  };
}