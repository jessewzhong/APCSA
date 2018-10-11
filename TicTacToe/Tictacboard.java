import java.util.Scanner;

public class Tictacboard { //sets up variables for the 2D array board and current column/row
  private char[][] board; 
  private int col;
  private int row;
  
  public Tictacboard(int n){ //sets up the board with all dashes "-"
    board = new char[n][n];
    for (int r = 0; r < board.length; r++)
   {
      for (int c = 0; c < board[0].length; c++)  
      {
         board[r][c] = '-';
      }
    }
  }
    
  public void displayBoard() { //displays the board in a spaced out format
    for (int r = 0; r < board.length; r++)
   {
      for (int c = 0; c < board[0].length; c++)  
      {
         System.out.print(board[r][c] + "\t");
      }
   System.out.println( );
    }
  }
  
  public void playGame(){ //plays the full game, uses the win variable to determine when to stop the loop, uses the counter to stop if there is a tie
    boolean win = false;
    int counter = 0;
    System.out.println("Empty board:");
    displayBoard();
    while (win == false){
      playGameX();
      if (checkWinnerX() == true){ //checks to see if X has won, breaks if true and prints
        win = true;
        System.out.println("X has won!");
        break;
      }
      counter++;
      if (counter == board.length * board[0].length){ //checks to see if board is full, so tie if true
        System.out.println("It's a tie!");
        break;
      }
      playGameO();
      if (checkWinnerO() == true){ //checks to see if O has won, breaks if true and prints
        win = true;
        System.out.println("O has won!");
      }
      counter++;
      if (counter == board.length * board[0].length){ //checks to see if board is full, so tie if true
        System.out.println("It's a tie!");
        break;
      }
    }
  }
  
  public void playGameX(){ //plays the game for X, uses temp variables to control loops such that the entered row/column are valid and that the selected row/column has no entry
    Scanner scan = new Scanner(System.in);
    int temprow = 0;
    int tempcol = 0;
    int temp = 0;
    System.out.println("X to move, enter desired row and column:");
    while(temp == 0){ //checks if row/column already has an entry or not
      while (temprow == 0){ //checks to see if row entered is valid or not
        System.out.println("Enter row:");
        row = scan.nextInt();
        if(row > board.length)
          System.out.println("Invalid row. Try again.");
        else 
          temprow = 1;
    }
      while (tempcol == 0){ //checks to see if column entered is valid or not
        System.out.println("Enter column:");
        col = scan.nextInt();
        if(col > board.length)
          System.out.println("Invalid column. Try again.");
        else 
          tempcol = 1;
      }
      if(board[row-1][col-1]!='X' && board[row-1][col-1]!='O') //ends loop if valid
        temp = 1;
      else { //goes through loop again if selected square not valid
        System.out.println("Selected square has already been chosen. Try again.");
        temprow = 0;
        tempcol = 0;
      }
    }
    board[row-1][col-1] = 'X';
    System.out.println("Current board:");
    displayBoard();
  }
  
  public void playGameO(){ //see commented code for playGameX above, basically identical setup
    Scanner scan = new Scanner(System.in);
    int temprow = 0;
    int tempcol = 0;
    int temp = 0;
    System.out.println("O to move, enter desired row and column:");
    while(temp == 0){
      while (temprow == 0){ 
        System.out.println("Enter row:");
        row = scan.nextInt();
        if(row > board.length)
          System.out.println("Invalid row. Try again.");
        else 
          temprow = 1;
    }
      while (tempcol == 0){ 
        System.out.println("Enter column:");
        col = scan.nextInt();
        if(col > board.length)
          System.out.println("Invalid column. Try again.");
        else 
          tempcol = 1;
      }
      if(board[row-1][col-1]!='X' && board[row-1][col-1]!='O')
        temp = 1;
      else {
        System.out.println("Selected square has already been chosen. Try again.");
        temprow = 0;
        tempcol = 0;
      }
    }
    board[row-1][col-1] = 'O';
    System.out.println("Current board:");
    displayBoard();
  }
  
  public boolean checkWinnerX(){ //checks to see if a row/column/diagonal are all Xs
    boolean endgame = true;
    for (int r = 0; r < board.length; r++) //checks to see if elements in the rows are the same; in each row, a flag variable checks the entries, breaks loop if true throughout row
   {
    boolean end = true;
    for (int c = 0; c < board[0].length; c++)  
      {
      if (board[r][c]!='X')
        end = false;
      }
    endgame = end;
    if (end == true)
      break;
    }
    if (endgame == true)
      return true;
    for (int c = 0; c < board[0].length; c++) //checks to see if elements in the columns are the same; in each column, a flag variable checks the entries, breaks loop if true throughout column
   {
    boolean end = true;
    for (int r = 0; r < board.length; r++)  
      {
      if (board[r][c]!='X')
        end = false;
      }
    endgame = end;
    if (end == true)
      break;
    }
    if (endgame == true)
      return true;
    endgame = true;
    for (int i = 0; i < board.length; i++){ //checks the main diagonal to see if all entries are Xs, flag checks true/false
      if (board[i][i]!='X')
        endgame = false;
    }
    if (endgame == true)
      return true;
    endgame = true;
    for (int i = 0; i < board.length; i++){ //checks the other diagonal to see if all entries are Xs
      if (board[i][board.length-i-1]!='X')
        endgame = false;
    }
    if (endgame == true)
      return true;
    return false;
  }
    
  public boolean checkWinnerO(){ //see the method checkWinnerX above, basically same method
    boolean endgame = true;
    for (int r = 0; r < board.length; r++)
   {
    boolean end = true;
    for (int c = 0; c < board[0].length; c++)  
      {
      if (board[r][c]!='O')
        end = false;
      }
    endgame = end;
    if (end == true)
      break;
    }
    if (endgame == true)
      return true;
    for (int c = 0; c < board[0].length; c++)
   {
    boolean end = true;
    for (int r = 0; r < board.length; r++)  
      {
      if (board[r][c]!='O')
        end = false;
      }
    endgame = end;
    if (end == true)
      break;
    }
    if (endgame == true)
      return true;
    endgame = true;
    for (int i = 0; i < board.length; i++){
      if (board[i][i]!='O')
        endgame = false;
    }
    if (endgame == true)
      return true;
    endgame = true;
    for (int i = 0; i < board.length; i++){
      if (board[i][board.length-i-1]!='O')
        endgame = false;
    }
    if (endgame == true)
      return true;
    return false;
  }
     
}
