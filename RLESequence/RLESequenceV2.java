/**
 * @author Jesse Zhong, jwz2111
 * Class stores an RLESequence in a compact form and performs the methods by converting back to expanded form V1,
 * saving space
 * Used this form because it is simple: it has two rows of arrays and it is easy to match
 * an element of one row (value) to the corresponding one (count) in the same column
 * @see V2tests.txt for tests.
 */
public class RLESequenceV2 {

	/**
	 * private 2D array field stores RLESequence in the form [[values list], [counts list]]
	 * Used this form because it is simple: it has two rows of arrays and it is easy to match
	 * an element of one row (value) to the corresponding one (count) in the same column
	 */
	private int[][] RLECompact;
	
	/**
	 * constructor creates a compact RLESequence with int array input
	 * @param sequence: int array
	 */
	public RLESequenceV2(int[] sequence) {
		for (int i = 0; i < sequence.length; i++) { //checks to see if all values are in bounds, adjusts accordingly
			if (sequence[i] > 255) {
				sequence[i] = 255;
				System.out.println("Value at index " + i + " exceeds 255, replaced with 255.");
			}
			if (sequence[i] < 0) {
				sequence[i] = 0;
				System.out.println("Value at index " + i + " is below 0, replaced with 0.");
			}
		}
		RLECompact = RLEConverter.toCompact(sequence);
	}
	
	/**
	 * default constructor creates empty sequence, makes sense because we want to start out
	 * with nothing, aka an empty 2D array
	 */
	public RLESequenceV2() {
		this(new int[0]);
	}
	
	/**
	 * constructor creates a sequence of length 'length' of all 255s (white default)
	 * @param length: int
	 */
	public RLESequenceV2(int length) {
		int[] temp = new int[length];
		for (int i = 0; i < temp.length; i++)
			temp[i] = 255;
		RLECompact = RLEConverter.toCompact(temp);
	}
	
	/**
	 * allows user to insert a value at a certain index (based on the expanded/V1 form) in the RLESequence
	 * @param index: int
	 * @param value: int
	 */
	public void insertValue(int index, int value) { //if RLESequence has length 0, user must insert at index 0
		RLESequenceV1 temp = new RLESequenceV1(RLEConverter.toSpaced(RLECompact));
		temp.insertValue(index, value);
		RLECompact = RLEConverter.toCompact(temp.getArray());
	}
	
	/**
	 * allows user to set a value at a certain index (based on the expanded/V1 form) in the RLESequence
	 * @param index: int
	 * @param value: int
	 */
	public void setValue(int index, int value) {
		RLESequenceV1 temp = new RLESequenceV1(RLEConverter.toSpaced(RLECompact));
		temp.setValue(index, value);
		RLECompact = RLEConverter.toCompact(temp.getArray());	
	}
	
	/**
	 * returns 2D array form of a compact RLESequence
	 * @return 2D int array
	 */
	public int[][] getArray() {
		return RLECompact;
	}
	
	/**
	 * allows user to get a value at a certain index (based on the expanded/V1 form) in the RLESequence 
	 * @param index: int
	 * @return int
	 */
	public int getValue(int index) {
			RLESequenceV1 temp = new RLESequenceV1(RLEConverter.toSpaced(RLECompact));
			return temp.getValue(index);
	}
	
	/**
	 * returns length of the RLESequence; ie number of total values
	 * @return int
	 */
	public int getLength() {
		RLESequenceV1 temp = new RLESequenceV1(RLEConverter.toSpaced(RLECompact));
		return temp.getLength();
	}
	
	/**
	 * checks to see if two RLESequenceVw elements are the same by converting to V1 form
	 * @param sequence: RLESequenceV2
	 * @return boolean
	 */
	public boolean equals(RLESequenceV2 sequence) {
		RLESequenceV1 temp1 = new RLESequenceV1(RLEConverter.toSpaced(RLECompact));
		RLESequenceV1 temp2 = new RLESequenceV1(RLEConverter.toSpaced(sequence.getArray()));
		return temp1.equals(temp2);
	}
	
	/**
	 * private method returns the condensed string format of RLESequence in the form [[values] [counts]]
	 */
	private String toStringPrivate() {
		String temp = "[";
		for (int i = 0; i < RLECompact.length; i++) { //iterates through rows
			temp += "[";
			for (int j = 0; j < RLECompact[i].length; j++) { //iterates through columns
				temp += RLECompact[i][j];
				if (j != RLECompact[i].length - 1) //adds space if not last item 
					temp += " ";
			}
			temp += "]";
			if (i != RLECompact.length - 1)
				temp += " ";
		}
		temp += "]";
		return temp;
	}
	
	/**
	 * method returns expanded/V1 representation of the RLESequence
	 */
	public String toString() {
		String temp = "[";
		for (int i = 0; i < RLECompact[0].length; i++) {
			for (int j = 0; j < RLECompact[1][i]; j++) {
				temp += RLECompact[0][i];
				if (!(j == RLECompact[1][i] - 1 && i == RLECompact[0].length - 1)) // adds space except last one
					temp += " ";
			}
		}
		temp += "]";
		return temp;
	}
	
	/**
	 * method adds the input sequence to the front of the given RLESequence
	 * @param sequence RLESequenceV2
	 */
	public void addToHead(RLESequenceV2 sequence) {
		RLESequenceV1 temp1 = new RLESequenceV1(RLEConverter.toSpaced(RLECompact));
		RLESequenceV1 temp2 = new RLESequenceV1(RLEConverter.toSpaced(sequence.getArray()));
		temp1.addToHead(temp2);
		RLECompact = RLEConverter.toCompact(temp1.getArray());		
	}
	
	/**
	 * method adds the input sequence to the back of the given RLESequence
	 * @param sequence RLESequenceV2
	 */
	public void addToTail(RLESequenceV2 sequence) {
		RLESequenceV1 temp1 = new RLESequenceV1(RLEConverter.toSpaced(RLECompact));
		RLESequenceV1 temp2 = new RLESequenceV1(RLEConverter.toSpaced(sequence.getArray()));
		temp1.addToTail(temp2);
		RLECompact = RLEConverter.toCompact(temp1.getArray());
	}
	
}
