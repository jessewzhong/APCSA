import java.util.Scanner;
/**
 * @author Jesse Zhong, jwz2111
 * 
 * The class contains methods to determine who is playing the computer:
 * either the user who inputs values or a simulated person 
 * @see Sim class
 *
 */
public class Talker {
	/**
	 * stores user throw value
	 */
	private char yourThrow;

	public Talker() {

	}
	
	/**
	 * method allows the user to input characters to play, checks validity
	 * 
	 * when a real friend is playing against the computer, it seems that
	 * he/she usually loses more than wins, confirming the fallacy indicated 
	 * in the assignment file.
	 */
	public void useUser() {
		Scanner scan = new Scanner (System.in);
		char inThrow = 'm';
		while (testValidThrow(inThrow) == false) {
			System.out.println("Enter a valid character");
			String inThrowString = scan.nextLine();
			inThrowString += " ";
			inThrow = inThrowString.charAt(0);
		}
		yourThrow = inThrow;
	}
	
	/**
	 * method uses a simulated player to determine the user's throw,
	 * dependent on the user's previous turn's win status
	 * performance: using the simulated player resulted in a surprisingly lower computer
	 * win rate, probably because it counters the simple AI in some way? I chose 1000 tests
	 * to ensure that both the AI and Sim could have enough data points such that randomness
	 * would not play a major role. I used Sim as a separate class in order to store its
	 * algorithms in a neat manner.
	 * @param inSim: instance of a simulated player
	 * @param inCounter: uses the data from the Counter class
	 */
	public void useSim(Sim inSim, Counter inCounter) {
		if (inCounter.getYourWinStatus() == true) {
			inSim.useWin(inCounter);
			yourThrow = inSim.getSimThrow();
		}
		else {
			inSim.useLoseOrTie();
			yourThrow = inSim.getSimThrow();
		}
	}
	
	/**
	 * randomly simulated throws
	 * @param inSim: instance of Sim
	 */
	public void useSimRandom(Sim inSim) {
		yourThrow = inSim.getSimThrow();
	}
	
	/**
	 * prints out welcome message for the game
	 */
	public static void welcome() {
		System.out.println("Welcome to the RPSSL game!");
		System.out.println("As a reminder, rock beats scissors and lizard, paper beats rock and Spock, scissors beats paper and lizard, Spock beats scissors and rock, and lizard beats Spock and paper.");
		System.out.println("When prompted, enter \'r\' for rock, \'p\' for paper, \'s\' for scissors, \'S\' for Spock, and \'l\' for lizard.");
	}
	
	/**
	 * checks validity of a character input 
	 * @param inThrow a character input
	 * @return true/false
	 */
	public static boolean testValidThrow(char inThrow) {
		for (int i = 0; i < Rules.RPS_CHARS.length; i++) {
			if (inThrow == Rules.RPS_CHARS[i]) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * returns the user's throw
	 * @return character of user's throw
	 */
	public char getYourThrow() {
		return yourThrow;
	}

}

