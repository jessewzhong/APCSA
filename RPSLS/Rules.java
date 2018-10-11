/**
 * @author Jesse Zhong, jwz2111
 * 
 * Class holds the static, final arrays that act as rules for the game
 * 
 *includes methods to convert data types relating to the game
 *
 */
public class Rules {
	public Rules() {
		
	}
	/**
	 * static final arrays to hold game data
	 * these help form the rules because of how the rules relate to modular arithmetic:
	 * when we look at the differences in the pairs of wins and losses in modulus, it 
	 * is constant (look at r p s S l r p s ...)
	 */
	public static final char[] RPS_CHARS = {'r', 'p', 's', 'S', 'l'}; 
	public static final String[] RPS_STRINGS = {"rock", "paper", "scissors", "Spock", "lizard"};
	
	/**
	 * 
	 * @param charOfThrow: char input
	 * @return integer index of char in RPS_CHARS
	 */
	public static int getIndex(char charOfThrow) {
		for (int i = 0; i < RPS_CHARS.length; i++) {
			if (charOfThrow == RPS_CHARS[i])
				return i;
		}
		return 0;
	}
	
	/**
	 * 
	 * @param charOfThrow: char input
	 * @return String reprsenetation of char in RPS_STRINGS
	 */
	public static String getString(char charOfThrow) {
		for (int i = 0; i < RPS_CHARS.length; i++) {
			if (charOfThrow == RPS_CHARS[i])
				return RPS_STRINGS[i];
		}
		return "rock";
	}
}
