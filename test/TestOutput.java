
public class TestOutput {
    public static void main(String[] args) {
      String description = "looking around";
      Player player = new Player("John", "Smith", 36);
      player.incrementAge();
      System.out.println(player.description(description));
      System.out.println(lastPlayerName);
    };
    static String lastPlayerName;
    public static class Player {
      public Player(String firstName, String lastName, long age) {
        this.age = age;
        this.lastName = lastName;
        this.firstName = firstName;
        lastPlayerName = this.firstName + " " + this.lastName;
      };
      String firstName;
      String lastName;
      long age = 18;
      public void incrementAge() {
         this.age++;
      };
      public String description(String description) {
        return this.firstName + " " + this.lastName + ", " + this.age + " years old, is " + description;
      }
    }
}
