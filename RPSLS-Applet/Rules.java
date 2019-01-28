/**
 * class sets the rules for the RPSLS game, based of Assignment 1
 * 
 * @author Jesse Zhong, jwz2111
 *
 */
public class Rules {
	public Rules() {
		
	}
	
	/**
	 * array stores the String values
	 */
	public static final String[] RPSLS_STRINGS = {"Rock", "Paper", "Scissors", "Spock", "Lizard"};
	
	/**
	 * converts a String to int index
	 * @param name String
	 * @return int
	 */
	public static int getIndex(String name) {
		for (int i = 0; i < RPSLS_STRINGS.length; i++) {
			if (name.equals(RPSLS_STRINGS[i]))
				return i;
		}
		return 0;
	}
	
	/**
	 * checks if first String beats second String
	 * @param a String
	 * @param b String
	 * @return boolean
	 */
	public static boolean checkWin(String a, String b) { //method uses the principle of modular arithmetic, looking at the patterns of wins/loses of "r p s S l", see Rules
		if (a.equals("BlackHole") || b.equals("BlackHole")) {
			return true; //returns true if either String is a black hole
		}
		//else, compares the two throws
		int indexA = getIndex(a);
		int indexB = getIndex(b);
		if (Math.floorMod((indexA - indexB), Rules.RPSLS_STRINGS.length) == 1 || Math.floorMod((indexA - indexB), Rules.RPSLS_STRINGS.length) == 3) {
			return true;
		}
		return false; //returns false if a doesn't beat b
	}
	
	
}
