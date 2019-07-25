package examples.test;

public class Main_ {
  public static void main(String[] args) {
    String description = "looking around";
    Player player = new Player("John", "Smith", 36);
    player.incrementAge();
    System.out.println(player.description());
  }
}