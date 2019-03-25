package examples.test;
import java.util.LinkedList;
class Main {
  static public void main(String[] args) {
    String description = " looking around";
    Player player = new Player("John", "Smith", 36);
    player.incrementAge();
    System.out.println(player.description() + description);
    LinkedList<String> list = new LinkedList<String>();
    list.add("first");
    String first = list.getFirst();
    System.out.println(first);
  }
}