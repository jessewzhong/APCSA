import java.util.Scanner;
import java.lang.Math; 

class TictacboardMain {
  public static void main(String[] args){
    Scanner scan = new Scanner (System.in);
    System.out.println("Welcome to The Game. Enter board size:");
    int n = scan.nextInt();
    Tictacboard x = new Tictacboard(n);
    x.playGame();
    System.out.println("Final board configuration:");
    x.displayBoard();
    
  }
}
