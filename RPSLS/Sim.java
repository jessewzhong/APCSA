import java.lang.Math;
/**
 * @author Jesse Zhong, jwz2111
 *
 *Class stores the methods that the simulated player will use
 *when playing against the computer
 */
public class Sim {
	/**
	 * stores Sim throw value
	 */
	private char simThrow;
	
	/**
	 * default constructor that randomly throws a character
	 */
	public Sim() {
		int index = (int)(Math.random() * Rules.RPS_CHARS.length);
		simThrow = Rules.RPS_CHARS[index];
	}
	
	/**
	 * characterizes the simulated player's move when it has won the previous round
	 * basically chooses a new throw, a dynamic method that isn't that predictable, 
	 * making it seem less robotic
	 * @param inCounter
	 */
	public void useWin(Counter inCounter) {
		char[] poss = new char[4];
		for (int i = 0; i < Rules.RPS_CHARS.length; i++) { //creates array with throws besides the last one by the user
			if (Rules.RPS_CHARS[i] != inCounter.getYourLastThrow())
				for (int j = 0; j < poss.length; j++) {
					if (poss[j] == '\u0000') {
						poss[j] = Rules.RPS_CHARS[i];
						break;
					}
				}
				
		}
		int index = (int)(Math.random() * 4); //chooses random index
		simThrow = poss[index];
	}
	
	/**
	 * characterizes the simulated player's move when it hasn't won the previous round
	 * chooses a random throw, kind of like a human's reaction to losing
	 */
	public void useLoseOrTie() {
		int index = (int)(Math.random() * Rules.RPS_CHARS.length);
		simThrow = Rules.RPS_CHARS[index];
	}
	
	/**
	 * returns simulated player's throw
	 * @return char
	 */
	public char getSimThrow() {
		return simThrow;
	}
}
