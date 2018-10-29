/**
 * @author Jesse Zhong, jwz2111
 * Class stores an RLESequence in a expanded form (int array) that contains various methods
 * used an int array because it seems natural based on the data given, especially the use case specificity
 * @see V1tests.txt for tests.
 */
public class RLESequenceV1 {
	
	/**
	 * stores RLESequence in an int array field
	 * used an int array because it seems natural based on the data given, especially the use case specificity
	 */
	private int[] RLESequence;
	
	/**
	 * constructor sets length of sequence to be 'length' and elements 255 (white default)
	 * @param length: int
	 */
	public RLESequenceV1(int length) {
		RLESequence = new int[length];
		for (int i = 0; i < RLESequence.length; i++)
			RLESequence[i] = 255;
	}
	
	/**
	 * default constructor creates empty array, makes sense because if we make a sequence, we
	 * want to start with nothing, aka an empty array
	 */
	public RLESequenceV1() {
		this(0);
	}
	
	/**
	 * constructor takes a given int array and copies it into the RLESequence,
	 * checking to see if values are in range
	 * @param entireSequence: int array
	 */
	public RLESequenceV1(int[] entireSequence) {
		RLESequence = entireSequence.clone();
		for (int i = 0; i < RLESequence.length; i++) {
			if (RLESequence[i] < 0) {
				System.out.println("int at index " + i + " is below 0, replaced with 0.");
				RLESequence[i] = 0;
			}
			if (RLESequence[i] > 255) {
				System.out.println("int at index " + i + " exceeds 255, replaced with 255.");
				RLESequence[i] = 255;
			}
		}
	}
	
	/**
	 * method inserts a value at a given index
	 * @param index: int
	 * @param value: int
	 */
	public void insertValue(int index, int value) { //if RLESequence has length 0, user must insert at index 0
		value = checkValue(value); // checks/adjusts value
		int[] temp = new int[RLESequence.length + 1];
		try {
			for (int i = 0; i < index; i++)
				temp[i] = RLESequence[i];
			temp[index] = value;
			for (int i = index + 1; i < temp.length; i++)
				temp[i] = RLESequence[i-1];
			RLESequence = temp.clone();
		}
		catch (IndexOutOfBoundsException e) {
			System.out.println("Error: index requested is out of range. Try again."); //error thrown if index out of bounds
		}
	}
	
	/**
	 * method sets a value at a certain index
	 * @param index: int
	 * @param value: int
	 */
	public void setValue(int index, int value) {
		value = checkValue(value); //checks/adjusts value
		try {
			RLESequence[index] = value;
		}
		catch (IndexOutOfBoundsException e) {
			System.out.println("Error: index requested is out of range. Try again."); //error thrown if index out of bounds
		}
	}
	
	/**
	 * method returns value at certain index
	 * @param index: int
	 * @return int
	 */
	public int getValue(int index) {
		try{
			return RLESequence[index];
		}
		catch (IndexOutOfBoundsException e) {
			System.out.println("Error: index requested is out of range. Try again. Returned a 0."); //error thrown if index out of bounds
			return 0;
		}
	}
	
	/**
	 * method returns the length of the RLESequence
	 * @return int
	 */
	public int getLength() {
		return RLESequence.length;
	}
	
	/**
	 * method returns the int array representation of RLESequence
	 * @return int
	 */
	public int[] getArray() {
		return RLESequence;
	}
	
	/**
	 * method checks to see if two RLESequenceV1s are equal by seeing if elements match
	 * @param sequence: RLESequenceV1
	 * @return boolean
	 */
	public boolean equals(RLESequenceV1 sequence) {
		if (RLESequence.length != sequence.getLength()) //checks length first
			return false;
		for (int i = 0; i < RLESequence.length; i++) {
			if (RLESequence[i] != sequence.getValue(i))
				return false;
			}
			return true;
	}
	
	/**
	 * returns String representation of the sequence in the form [values]
	 */
	public String toString() {
		String temp = "[";
		for (int i = 0; i < RLESequence.length; i++) {
			temp += RLESequence[i];
			if (i != RLESequence.length - 1)
				temp += " ";
		}
		temp += "]";
		return temp;
	}
	
	/**
	 * method adds the input sequence to the front of the given sequence
	 * @param sequence: RLESequenceV1
	 */
	public void addToHead(RLESequenceV1 sequence) {
		int[] temp = new int[RLESequence.length + sequence.getLength()];
		for (int i = 0; i < sequence.getLength(); i++)
			temp[i] = sequence.getValue(i);
		for (int i = sequence.getLength(); i < temp.length; i++)
			temp[i] = RLESequence[i - sequence.getLength()];
		RLESequence = temp.clone();		
	}
	
/**
 * method adds the input sequence to the back of the given sequence
 * @param sequence: RLESequenceV1
 */
	public void addToTail(RLESequenceV1 sequence) {
		int[] temp = new int[RLESequence.length + sequence.getLength()];
		for (int i = 0; i < RLESequence.length; i++)
			temp[i] = RLESequence[i];
		for (int i = RLESequence.length; i < temp.length; i++)
			temp[i] = sequence.getValue(i - RLESequence.length);
		RLESequence = temp.clone();	
	}
	
	/**
	 * helper method adjusts the value based on validity
	 * @param value: int
	 * @return int
	 */
	private int checkValue(int value) {
		if (value < 0) {
			System.out.println("value is below 0, replaced with 0.");
			return 0;
		}
		if (value > 255) {
			System.out.println("value exceeds 255, replaced with 255.");
			return 255;
		}
		return value; //returns original if it is fine
	}
	
}
