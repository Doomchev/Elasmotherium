public class NewClass {
  public static void main(String[] args) {
    int max = 0, k = 0;
    for(int i = 0; i < 1000000; i++) {
      k++;
      if(Math.floor(Math.random()*7) == 6) {
        if(k > max) {
          max = k;
          System.out.println(max);
          k = 0;
        }
      }
    }
  }
}
