import java.lang.Math;
/**
 * @author Jesse Zhong, jwz2111
 * 
 * Class contains methods that check for the winner and compiles throw
 * information from both the user and computer
 *
 */
public class WinnerChecker {
	/**
	 * stores data for throws of both user/computer
	 */
	private int compIndex;
	private int yourIndex;
	private String compString;
	private String yourString;
	
	/**
	 * constructor sets the values of the throws from the user/computer data
	 * @param inThrower: Thrower class
	 * @param inTalker: Talker class
	 */
	public WinnerChecker(Thrower inThrower, Talker inTalker) {
		compIndex = Rules.getIndex(inThrower.getCompThrow());
		yourIndex = Rules.getIndex(inTalker.getYourThrow());
		compString = Rules.getString(inThrower.getCompThrow());
		yourString = Rules.getString(inTalker.getYourThrow());
	}
	
	/**
	 * checks if computer wins
	 * @return true/false
	 */
	public boolean checkCompWin() { //method uses the principle of modular arithmetic, looking at the patterns of wins/loses of "r p s S l", see Rules
		if (Math.floorMod((compIndex - yourIndex), Rules.RPS_CHARS.length) == 1 || Math.floorMod((compIndex - yourIndex), Rules.RPS_CHARS.length) == 3)
			return true;
		return false;
	}
	
	/**
	 * checks if user wins
	 * @return true/false
	 */
	public boolean checkYourWin() { //reasoning as above
		if (Math.floorMod((yourIndex - compIndex), Rules.RPS_CHARS.length) == 1 || Math.floorMod((yourIndex - compIndex), Rules.RPS_CHARS.length) == 3)
			return true;
		return false;
	}
	
	/**
	 * checks if there is tie
	 * @return true/false
	 */
	public boolean checkTie() {
		if (yourIndex == compIndex)
			return true;
		return false;
	}
	
	/**
	 * prints the summary of the round after each throw
	 */
	public void printMessage() {
		if (checkCompWin() == true)
			System.out.println("I win: My " + compString + " beats your " + yourString);
		else if (checkYourWin() == true)
			System.out.println("You win: Your " + yourString + " beats my " + compString);
		else if (checkTie() == true)
			System.out.println("We tied: We both played " + compString);
	}
	
	/**
	 * returns the index of the user's throw
	 * @return int index of throw
	 */
	public int getYourIndex() {
		return yourIndex;
	}
}
