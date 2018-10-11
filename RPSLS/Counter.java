/**
 * @author Jesse Zhong, jwz2111
 * 
 * Class stores all the information that is accumulated through the rounds
 * and is utilized in the AI methods and printing final data
 *
 */
public class Counter {
	/**
	 * stores the data of rounds, such as wins, ties, games, an array that holds the number
	 * of throws the user has made by type, win status of user, and the last/secondtolast throws
	 */
	private int compWins;
	private int yourWins;
	private int ties;
	private int totalGames;
	private int[] yourThrowsCount;
	private boolean yourWinStatus;
	private char yourLastThrow;
	private char yourSecondToLastThrow;
	
	/**
	 * defaut constructor that initializes the array to fit the game
	 */
	public Counter() {
		yourThrowsCount = new int[Rules.RPS_CHARS.length];
	}
	
	/**
	 * updates all the data in the game so far
	 * @param inChecker: WinnerChecker object type
	 */
	public void updateCounter(WinnerChecker inChecker) {
		if (inChecker.checkCompWin() == true) {
			yourWinStatus = false;
			compWins++;
		}
		else if (inChecker.checkYourWin() == true) {
			yourWinStatus = true;
			yourWins++;
		}
		else {
			yourWinStatus = false;
			ties++;
		}
		totalGames++;
		for (int i = 0; i < yourThrowsCount.length; i++ ) {
			if (inChecker.getYourIndex() == i)
				yourThrowsCount[i]++;
		}
		yourSecondToLastThrow = yourLastThrow;
		yourLastThrow = Rules.RPS_CHARS[inChecker.getYourIndex()];
		
	}
	
	/**
	 * prints the data of all the rounds at the end of the game
	 */
	public void printMessage() {
		System.out.println("I've won " + compWins + " games " + "(" + (((double)compWins / totalGames) * 100) + "%)");
		System.out.println("You've won " + yourWins + " games " + "(" + (((double)yourWins / totalGames) * 100) + "%)");
		System.out.println("We've tied " + ties + " games " + "(" + (((double)ties / totalGames) * 100) + "%)");	
	}
	
	/**
	 * returns array of the user's types of throws
	 * @return array
	 */
	public int[] getYourThrowsCount() {
		return yourThrowsCount;
	}
	
	/**
	 * returns total number of rounds/games
	 * @return int
	 */
	public int getTotalGames() {
		return totalGames;
	}
	
	/**
	 * returns whether user won the last game
	 * @return true/false
	 */
	public boolean getYourWinStatus() {
		return yourWinStatus;
	}
	
	/**
	 * returns the user's last throw
	 * @return char
	 */
	public char getYourLastThrow() {
		return yourLastThrow;
	}
	
	/**
	 * returns whether user won the second to last game
	 * @return true/false
	 */
	public char getYourSecondToLastThrow() {
		return yourSecondToLastThrow;
	}
}
