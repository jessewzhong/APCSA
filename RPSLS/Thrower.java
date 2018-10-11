import java.lang.Math;
/**
 * @author Jesse Zhong, jwz2111
 * 
 * Class determines the throw that the computer makes:
 * uses a random throw (default constructor) or
 * uses a simple AI
 * uses a complex AI
 *
 */
public class Thrower {
	/**
	 * stores computer throw value
	 */
	private char compThrow;
	
	/**
	 * default constructor that randomly outputs a move
	 */
	public Thrower() {
		int index = (int)(Math.random() * Rules.RPS_CHARS.length);
		compThrow = Rules.RPS_CHARS[index];
	}
	
	/**
	 * a simple AI method that looks at the user's aggregation of previous throws
	 * and computes a weighted random algorithm to determine which object to throw
	 * performance: consistently wins more than loses to the user, but usually by a marginal amount,
	 * fails horribly if user uses a consistent entry pattern
	 * @param inCounter: uses data from the Counter class
	 * @see 2.2tests.txt
	 */
	public void useAI(Counter inCounter) {
		int total = inCounter.getTotalGames();
		int[] weighted = new int[inCounter.getYourThrowsCount().length];
		for (int i = 0; i < weighted.length; i++) //creates an array of weighted values based on the throw counts
			weighted[i] = inCounter.getYourThrowsCount()[i];
		double index = Math.random() * total;
		for (int i = 0; i < weighted.length; i++) { //weighted random algorithm
			index -= weighted[i];
			if (index <= 0) {
				compThrow = Rules.RPS_CHARS[(i+1) % Rules.RPS_CHARS.length];
				break;
			}
		}	
	}
	
	/**
	 * a more involved AI that looks at:
	 * 1) if the past two inputs of the user are the same, it predicts the next will be different,
	 *    so the AI chooses randomly from the other three objects that do not beat the repeated input
	 * 2) if the user just won the last round, the AI predicts the next move will be different, and uses
	 *    the same method as above.
	 * 3) if the user's last input was one above or one below the second to last input, the AI
	 *    will predict that the user continues the pattern, choosing a throw that will beat the 
	 *    predicted input
	 * 4) uses the useAI() method if needed after
	 * performance: consistently better than the simple AI method, always at a 40%+ win rate when
	 * competing against a simulated user. Chose 1000 tests so that there would be marginal impact
	 * due to random error.
	 * @see 2.5tests.txt
	 * @param inCounter: uses data from the Counter class
	 */
	public void useMAIGA(Counter inCounter) {
		if (inCounter.getYourLastThrow() == inCounter.getYourSecondToLastThrow() || inCounter.getYourWinStatus() == true) { //checks if last two moves of user are the same or if user won last time
			char firstRemove = Rules.RPS_CHARS[(Rules.getIndex(inCounter.getYourLastThrow()) + 3) % Rules.RPS_CHARS.length]; //the two moves that would beat the repeated throw
			char secondRemove = Rules.RPS_CHARS[(Rules.getIndex(inCounter.getYourLastThrow()) + 1) % Rules.RPS_CHARS.length];
			char[] poss = new char[3];
			for (int i = 0; i < Rules.RPS_CHARS.length; i++) { //creates array that holds the other three throws to choose from
				if ((Rules.RPS_CHARS[i] != firstRemove) && (Rules.RPS_CHARS[i] != secondRemove))
					for (int j = 0; j < poss.length; j++) {
						if (poss[j] == '\u0000') {
							poss[j] = Rules.RPS_CHARS[i];
							break;
						}
					}
					
			}
			int index = (int)(Math.random() * 3); //choosing random char from 3 choices
			compThrow = poss[index];
		}
		else if (Rules.getIndex(inCounter.getYourLastThrow()) == (Rules.getIndex(inCounter.getYourSecondToLastThrow()) + 1) % Rules.RPS_CHARS.length){ //checks for increasing pattern
			int predictedIndex = (Rules.getIndex(inCounter.getYourLastThrow()) + 1) % Rules.RPS_CHARS.length;
			compThrow = Rules.RPS_CHARS[(predictedIndex + 1) % Rules.RPS_CHARS.length];
		}
		else if (Rules.getIndex(inCounter.getYourSecondToLastThrow()) == (Rules.getIndex(inCounter.getYourLastThrow()) + 1) % Rules.RPS_CHARS.length){ //checks for decreasing pattern
			int predictedIndex = Math.floorMod((Rules.getIndex(inCounter.getYourLastThrow()) - 1), Rules.RPS_CHARS.length);
			compThrow = Rules.RPS_CHARS[(predictedIndex + 1) % Rules.RPS_CHARS.length];
		}
		else 
			useAI(inCounter);		
	}
	
	/**
	 * returns the throw of the computer
	 * @return character of the computer's throw
	 */
	public char getCompThrow() {
		return compThrow;
	}
	
}
