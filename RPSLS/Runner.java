/**
 * @author Jesse Zhong, jwz2111
 * 
 *This is the runner class, creates an overarching Counter object and loops through the number 
 *of times that the user desires to test.
 *
 *You can use/remove the useAI method of the Thrower class to change what the computer outputs
 *
 *You can use methods of the Talker class to either simulate or physically play the game
 *
 *@see 2.1tests, 2.2tests, 2.3tests, 2.4tests, 2.5tests text files for run simulation outputs
 *
 */
public class Runner {
	public static void main (String[] args) {
		Talker.welcome();
		Counter currentCounter = new Counter();
		for (int i = 0; i < 100; i++) { //choose number of games here
			Thrower currentThrower= new Thrower();
			currentThrower.useMAIGA(currentCounter); //uses the MAIGA method of Thrower
			Talker currentTalker = new Talker();
			Sim currentSim = new Sim();
			//currentTalker.useSim(currentSim, currentCounter); //uses the sim method of Talker, simulated smart user
			//currentTalker.useSimRandom(currentSim); //uses the sim method of Talker, simulated user
			currentTalker.useUser(); //implement if user is playing
			WinnerChecker currentChecker = new WinnerChecker(currentThrower, currentTalker);
			currentChecker.printMessage();
			currentCounter.updateCounter(currentChecker);
		}
		System.out.println("The game has ended.");
		currentCounter.printMessage();
	}

}
