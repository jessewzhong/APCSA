/**
 * @author Jesse Zhong, jwz2111
 * Tester class sets up methods to test each of the RLESequence types,
 * all based on the same framework/model in order to compare
 * the outputs to see if they are 'identical'
 *
 */
public class Tester {
	
	/**
	 * fields are various RLESequences to test
	 */
	private RLESequenceV1 a1;
	private RLESequenceV1 a2;
	private RLESequenceV1 a3;
	private RLESequenceV1 a4;
	private RLESequenceV1 a5;
	private RLESequenceV2 b1;
	private RLESequenceV2 b2;
	private RLESequenceV2 b3;
	private RLESequenceV2 b4;
	private RLESequenceV2 b5;
	private RLESequenceV3 c1;
	private RLESequenceV3 c2;
	private RLESequenceV3 c3;
	private RLESequenceV3 c4;
	private RLESequenceV3 c5;
	
	public Tester() {
		
	}
	
	/**
	 * tests V1 sequences
	 */
	public void testV1() {
		System.out.println("Testing V1");
		System.out.println();
		System.out.println("Error messages when instantiating a1:");
		a1 = new RLESequenceV1();
		System.out.println("Error messages when instantiating a2:");
		a2 = new RLESequenceV1(10);
		System.out.println("Error messages when instantiating a3:");
		a3 = new RLESequenceV1(new int[] {100, 23, 45, 24, 260, 260, 143, 111});
		System.out.println("Error messages when instantiating a4:");
		a4 = new RLESequenceV1(new int[] {111, 82, 123, -23, 43, 43, 43, 100});
		System.out.println("Error messages when instantiating a5:");
		a5 = new RLESequenceV1(new int[] {111, 90, 123, 0, 43, 43, 43, 100});
		System.out.println();
		System.out.println("Printing the RLESequences we are using:");
		System.out.println("a1: " + a1);
		System.out.println("a2: " + a2);
		System.out.println("a3: " + a3);
		System.out.println("a4: " + a4);
		System.out.println("a5: " + a5);
		System.out.println();
		System.out.println("Testing the insertValue method");
		System.out.println("Inserting 1 to empty a1:");
		a1.insertValue(0, 1);
		System.out.println("a1: " + a1);
		System.out.println("Inserting 24 at index 4 to a3:");
		a3.insertValue(4, 24);
		System.out.println("a3: " + a3);
		System.out.println();
		System.out.println("Testing the setValue method");
		System.out.println("Setting 270 to index 6 of a3:");
		a3.setValue(6, 270);
		System.out.println("a3: " + a3);
		System.out.println("Setting 90 to index 1 of a4:");
		a4.setValue(1, 90);
		System.out.println("a4: " + a4);
		System.out.println();
		System.out.println("Testing the getValue method");
		System.out.println("Getting value at index 3 of a4:");
		System.out.println("a4: " + a4.getValue(3));
		System.out.println("Getting value at index 100 of a2:");
		System.out.println("a4: " + a2.getValue(100));
		System.out.println();
		System.out.println("Testing the getLength method");
		System.out.println("Getting length of a3:");
		System.out.println("a3: " + a3.getLength());
		System.out.println();
		System.out.println("Testing the equals method");
		System.out.println("Testing a1 and a2:");
		System.out.println("a1 and a2: " + a1.equals(a2));
		System.out.println("Testing a4 and a5:");
		System.out.println("a4 and a5: " + a4.equals(a5));
		System.out.println();
		System.out.println("Testing the addToHead method");
		System.out.println("Testing a4.addToHead(a2):");
		a4.addToHead(a2);
		System.out.println("a4: " + a4);
		System.out.println();
		System.out.println("Testing the addToTail method");
		System.out.println("Testing a3.addToTail(a5):");
		a3.addToTail(a5);
		System.out.println("a3: " + a3);	
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println();
		System.out.println();
	}
	
	/**
	 * test V2 sequences
	 */
	public void testV2() {
		System.out.println("Testing V2");
		System.out.println();
		System.out.println("Error messages when instantiating b1:");
		b1 = new RLESequenceV2();
		System.out.println("Error messages when instantiating b2:");
		b2 = new RLESequenceV2(10);
		System.out.println("Error messages when instantiating b3:");
		b3 = new RLESequenceV2(new int[] {100, 23, 45, 24, 260, 260, 143, 111});
		System.out.println("Error messages when instantiating b4:");
		b4 = new RLESequenceV2(new int[] {111, 82, 123, -23, 43, 43, 43, 100});
		System.out.println("Error messages when instantiating b5:");
		b5 = new RLESequenceV2(new int[] {111, 90, 123, 0, 43, 43, 43, 100});
		System.out.println();
		System.out.println("Printing the RLESequences we are using:");
		System.out.println("b1: " + b1);
		System.out.println("b2: " + b2);
		System.out.println("b3: " + b3);
		System.out.println("b4: " + b4);
		System.out.println("b5: " + b5);
		System.out.println();
		System.out.println("Testing the insertValue method");
		System.out.println("Inserting 1 to empty b1:");
		b1.insertValue(0, 1);
		System.out.println("b1: " + b1);
		System.out.println("Inserting 24 at index 4 to b3:");
		b3.insertValue(4, 24);
		System.out.println("b3: " + b3);
		System.out.println();
		System.out.println("Testing the setValue method");
		System.out.println("Setting 270 to index 6 of b3:");
		b3.setValue(6, 270);
		System.out.println("b3: " + b3);
		System.out.println("Setting 90 to index 1 of b4:");
		b4.setValue(1, 90);
		System.out.println("b4: " + b4);
		System.out.println();
		System.out.println("Testing the getValue method");
		System.out.println("Getting value at index 3 of b4:");
		System.out.println("b4: " + b4.getValue(3));
		System.out.println("Getting value at index 100 of b2:");
		System.out.println("b4: " + b2.getValue(100));
		System.out.println();
		System.out.println("Testing the getLength method");
		System.out.println("Getting length of b3:");
		System.out.println("b3: " + b3.getLength());
		System.out.println();
		System.out.println("Testing the equals method");
		System.out.println("Testing b1 and b2:");
		System.out.println("b1 and b2: " + b1.equals(b2));
		System.out.println("Testing b4 and b5:");
		System.out.println("b4 and b5: " + b4.equals(b5));
		System.out.println();
		System.out.println("Testing the addToHead method");
		System.out.println("Testing b4.addToHead(b2):");
		b4.addToHead(b2);
		System.out.println("b4: " + b4);
		System.out.println();
		System.out.println("Testing the addToTail method");
		System.out.println("Testing b3.addToTail(b5):");
		b3.addToTail(b5);
		System.out.println("b3: " + b3);
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println();
		System.out.println();
	}
	
	/**
	 * tests V3 sequences
	 */
	public void testV3() {
		System.out.println("Testing V3");
		System.out.println();
		System.out.println("Error messages when instantiating c1:");
		c1 = new RLESequenceV3();
		System.out.println("Error messages when instantiating c2:");
		c2 = new RLESequenceV3(10);
		System.out.println("Error messages when instantiating c3:");
		c3 = new RLESequenceV3(new int[] {100, 23, 45, 24, 260, 260, 143, 111});
		System.out.println("Error messages when instantiating c4:");
		c4 = new RLESequenceV3(new int[] {111, 82, 123, -23, 43, 43, 43, 100});
		System.out.println("Error messages when instantiating c5:");
		c5 = new RLESequenceV3(new int[] {111, 90, 123, 0, 43, 43, 43, 100});
		System.out.println();
		System.out.println("Printing the RLESequences we are using:");
		System.out.println("c1: " + c1);
		System.out.println("c2: " + c2);
		System.out.println("c3: " + c3);
		System.out.println("c4: " + c4);
		System.out.println("c5: " + c5);
		System.out.println();
		System.out.println("Testing the insertValue method");
		System.out.println("Inserting 1 to empty c1:");
		c1.insertValue(0, 1);
		System.out.println("c1: " + c1);
		System.out.println("Inserting 24 at index 4 to c3:");
		c3.insertValue(4, 24);
		System.out.println("c3: " + c3);
		System.out.println();
		System.out.println("Testing the setValue method");
		System.out.println("Setting 270 to index 6 of c3:");
		c3.setValue(6, 270);
		System.out.println("c3: " + c3);
		System.out.println("Setting 90 to index 1 of c4:");
		c4.setValue(1, 90);
		System.out.println("c4: " + c4);
		System.out.println();
		System.out.println("Testing the getValue method");
		System.out.println("Getting value at index 3 of c4:");
		System.out.println("c4: " + c4.getValue(3));
		System.out.println("Getting value at index 100 of c2:");
		System.out.println("c4: " + c2.getValue(100));
		System.out.println();
		System.out.println("Testing the getLength method");
		System.out.println("Getting length of c3:");
		System.out.println("c3: " + c3.getLength());
		System.out.println();
		System.out.println("Testing the equals method");
		System.out.println("Testing c1 and c2:");
		System.out.println("c1 and c2: " + c1.equals(c2));
		System.out.println("Testing c4 and c5:");
		System.out.println("c4 and c5: " + c4.equals(c5));
		System.out.println();
		System.out.println("Testing the addToHead method");
		System.out.println("Testing c4.addToHead(c2):");
		c4.addToHead(c2);
		System.out.println("c4: " + c4);
		System.out.println();
		System.out.println("Testing the addToTail method");
		System.out.println("Testing c3.addToTail(c5):");
		c3.addToTail(c5);
		System.out.println("c3: " + c3);
		System.out.println("Testing the changeValues method");
		System.out.println("Testing c3.changeValues(160)");
		c3.changeValues(160);
		System.out.println("c3: " + c3);
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println();
		System.out.println();
	}
	
	
}
