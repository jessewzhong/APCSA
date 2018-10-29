/**
 * @author Jesse Zhong, jwz2111
 * Runner class runs the tests using the Tester class 
 * RLESequenceV1: expanded data form, API methods
 * RLESequenceV2: compressed data form, algorithms to save space
 * RLESequenceV3: compressed data form, algorithms to save space and time
 * RLEConverter: helps convert expanded to compressed data forms, and vice versa
 * Tester: lays out test cases for V1, V2, V3
 * @see V1tests.txt, V2tests.txt, V3tests.txt, AllTests.txt
 */
public class Runner {
	

	public static void main(String[] args) {
		Tester myTester = new Tester();
		myTester.testV1();
		myTester.testV2();
		myTester.testV3();
	}

}
