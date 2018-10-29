/**
 * @author Jesse Zhong, jwz2111
 * Class stores an RLESequence in a compact form and performs the methods with the compact forms,
 * saving time and space
 * Used this form because it is simple: it has two rows of arrays and it is easy to match
 * an element of one row (value) to the corresponding one (count) in the same column
 * @see V3tests.txt for tests.
 */
public class RLESequenceV3 {

	/**
	 * private 2D array field stores RLESequence in the form [[values list], [counts list]]
	 * Used this form because it is simple: it has two rows of arrays and it is easy to match
	 * an element of one row (value) to the corresponding one (count) in the same column
	 */
	private int[][] RLECompact;
	
	/**
	 * @param sequence: int array
	 * constructor takes in an int array and uses RLEConverter to convert to compact form
	 */
	public RLESequenceV3(int[] sequence) {
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
	public RLESequenceV3() {
		this(new int[0]);
	}
	
	/**
	 * @param length: int
	 * constructor creates an RLESequence with all 255s of length 'length' (white default)
	 */
	public RLESequenceV3(int length) {
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
		if (index > this.getLength() || index < 0) {
			System.out.println("Error: index requested is out of range. Try again."); //displays error if out of bounds
			return;
		}
		value = checkValue(value); //checks/adjusts value
		index += 1; //since our new format doesn't start at '0'
		for (int i = 0; i < RLECompact[1].length; i++) { //checks which column of RLECompact that the index falls under
			index -= RLECompact[1][i];
			if (index <= 0 || (i == RLECompact[1].length - 1 && index == 1) ) { //allows insert at end, doesn't do anything if index > length + 1 (ie index out of bounds)
				if (RLECompact[0][i] == value) {								//works with the RLECompact column that the index is within, uses method splice if the 
					RLECompact[1][i]++;											//value is not the same as the value of the column, splicing the new value accordingly
				}
				else {
					index += RLECompact[1][i]; //reset index before it turned nonpositive
					splice(RLECompact, i, index - 1, RLECompact[1][i] - index + 1, value); //uses splice method
				}
				break;
			}
				
		}
		if (RLECompact[0].length == 0) { //if empty array, just add values
			RLECompact[0] = new int[] {value};
			RLECompact[1] = new int[] {1};
		}
		
	}
	
	/**
	 * allows user to set a value at a certain index (based on the expanded/V1 form) in the RLESequence
	 * @param index: int
	 * @param value: int
	 */
	public void setValue(int index, int value) {
		if (index > this.getLength() - 1 || index < 0) { //user can't set value for empty RLESequence
			System.out.println("Error: index requested is out of range. Try again."); //displays error if out of bounds
			return;
		}
		value = checkValue(value); //checks/adjusts value
		index += 1; //since our new format doesn't start at '0'
		for (int i = 0; i < RLECompact[1].length; i++) { 											
			index -= RLECompact[1][i];
			if (index <= 0) { 
				if (RLECompact[0][i] == value) {			      //works with the RLECompact column that the index is within, uses method splice if the 
					//do nothing cause replacing with same thing  //value is not the same as the value of the column, splicing the new value accordingly
				}	
				else {
					index += RLECompact[1][i]; //reset index before it turned nonpositive
					splice(RLECompact, i, index - 1, RLECompact[1][i] - index, value); //uses splice
				}
				break;
			}
				
		}	
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
		if (index < 0 || index > this.getLength() - 1) {
			System.out.println("Error: index requested is out of range. Try again. Returned a 0."); //what happens if index out of bounds
			return 0;
		}
		index += 1; //since our new format doesn't start at '0'
		for (int i = 0; i < RLECompact[1].length; i++) { //works with the RLECompact column that the index is within, returns value in column that index falls in
			index -= RLECompact[1][i];
			if (index <= 0) {
				return RLECompact[0][i];
			}
				
		}
		return 0; //java not smart enough
	}
	
	/**
	 * returns length of the RLESequence; ie number of total values
	 * @return int
	 */
	public int getLength() {
		int sum = 0;
		for (int i = 0; i < RLECompact[1].length; i++)
			sum += RLECompact[1][i];
		return sum;
	}
	
	/**
	 * checks to see if two RLESequenceV3 elements are the same by first converting to simplified form,
	 * then checking each value
	 * @param sequence: RLESequenceV3
	 * @return boolean
	 */
	public boolean equals(RLESequenceV3 sequence) {
		sequence.convertToSimplified(); //if somehow sequence is not "simplified," we simplify it then compare to make the algorithm easier
		if(RLECompact[0].length != sequence.getArray()[0].length)
			return false;
		for (int i = 0; i < RLECompact.length; i++) { //checks each value to match
			for (int j = 0; j < RLECompact[i].length; j++) {
				if (RLECompact[i][j] != sequence.getArray()[i][j])
					return false;
			}
		}
		
		return true;
	}
	
	/**
	 * private method returns the condensed string format of RLESequence in the form [[values] [counts]]
	 */
	private String toStringPrivate() { //returns condensed string format of RLESequence
		String temp = "[";
		for (int i = 0; i < RLECompact.length; i++) { //iterates for each row
			temp += "[";
			for (int j = 0; j < RLECompact[i].length; j++) { //iterates for each int in each row
				temp += RLECompact[i][j];
				if (j != RLECompact[i].length - 1)
					temp += " ";
			}
			temp += "]";
			if (i != RLECompact.length - 1) // adds space if not the last item
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
	 * @param sequence RLESequenceV3
	 */
	public void addToHead(RLESequenceV3 sequence) {
		int[] temp = new int[RLECompact[0].length + sequence.getArray()[0].length]; //creates new array[0] of RLECompact
		for (int i = 0; i < sequence.getArray()[0].length; i++) 
			temp[i] = sequence.getArray()[0][i];
		for (int i = sequence.getArray()[0].length; i < temp.length; i++)
			temp[i] = RLECompact[0][i - sequence.getArray()[0].length];
		int[] temp1 = new int[RLECompact[1].length + sequence.getArray()[1].length]; //creates new array[0] of RLECompact
		for (int i = 0; i < sequence.getArray()[1].length; i++) 
			temp1[i] = sequence.getArray()[1][i];
		for (int i = sequence.getArray()[1].length; i < temp1.length; i++)
			temp1[i] = RLECompact[1][i - sequence.getArray()[1].length];
		RLECompact[0] = temp.clone();
		RLECompact[1] = temp1.clone();
		try {
			if (RLECompact[0][sequence.getArray()[0].length - 1] == RLECompact[0][sequence.getArray()[0].length]) //tests to see if merges need to be made after combining
				mergeValues(RLECompact, sequence.getArray()[0].length - 1);										  // ie values are the same at the ends
		}
		catch (IndexOutOfBoundsException e) { //catches if you add something to beginning of empty array
		}
	}
	
	/**
	 * method adds the input sequence to the back of the given RLESequence
	 * @param sequence RLESequenceV3
	 */
	public void addToTail(RLESequenceV3 sequence) {
		int[] temp = new int[RLECompact[0].length + sequence.getArray()[0].length]; //creates new array[0] of RLECompact
		for (int i = 0; i < RLECompact[0].length; i++) 
			temp[i] = RLECompact[0][i];
		for (int i = RLECompact[0].length; i < temp.length; i++)
			temp[i] = sequence.getArray()[0][i - RLECompact[0].length];
		int[] temp1 = new int[RLECompact[1].length + sequence.getArray()[1].length]; //creates new array[1] of RLECompact
		for (int i = 0; i < RLECompact[1].length; i++) 
			temp1[i] = RLECompact[1][i];
		for (int i = RLECompact[1].length; i < temp1.length; i++)
			temp1[i] = sequence.getArray()[1][i - RLECompact[0].length];
		RLECompact[0] = temp.clone();
		RLECompact[1] = temp1.clone();
		try {
			if (RLECompact[0][RLECompact[0].length - sequence.getArray()[0].length - 1] == RLECompact[0][RLECompact[0].length - sequence.getArray()[0].length]) //test to see if merges
				mergeValues(RLECompact, RLECompact[0].length - sequence.getArray()[0].length - 1);																// need to be made
		}
		catch (IndexOutOfBoundsException e) { //catches if you add something to empty array
		}
	}
	
	/**
	 * creativity step: increments each value of the RLESequence by a certain amount 
	 * @param byWhat: int
	 */
	public void changeValues(int byWhat) {
		for (int i = 0; i < RLECompact[0].length; i++) { //checks if the values are still in bounds, sets to 0 or 255 accordingly
			RLECompact[0][i] += byWhat;
			if (RLECompact[0][i] > 255) { 
				System.out.println("Value at index " + getIndex(RLECompact, i) + " to " + (getIndex(RLECompact, i) + RLECompact[1][i] - 1) + 
						" (" + RLECompact[0][i] + ") now exceed 255, replaced with 255.");
				RLECompact[0][i] = 255;
			}
			if (RLECompact[0][i] < 0) {
				System.out.println("Value at index " + getIndex(RLECompact, i) + " to " + (getIndex(RLECompact, i) + RLECompact[1][i] - 1) + 
						" (" + RLECompact[0][i] + ") now below 0, replaced with 0.");
				RLECompact[0][i] = 0;
			}
		}
	}
	
	/**
	 * helper method returns the 'real' index of the expanded/V1 form that is at the start of a certain column in RLECompact
	 * @param array: int array
	 * @param indexOfArray: int
	 * @return int
	 */
	private int getIndex(int[][] array, int indexOfArray) {
		int sum = 0;
		for (int i = 0; i < indexOfArray; i++) {
			sum += array[1][i];
		}
		sum -= 1; //since our sum count in the int[][] starts at 1, not 0, as the norm
		sum += 1; //since we want our sum count to start at the beginning of the next "group" of value/count
		return sum;
	}
	
	/**
	 * helper method is at foundation of insert and set methods, basically splices the RLESequence in compact form by using how much
	 * of the old column is on the left and on the right, checking if merges need to be made as well
	 * @param array: int array
	 * @param index: int
	 * @param left: int
	 * @param right: int
	 * @param newValue: int
	 */
	private void splice(int[][] array, int index, int left, int right, int newValue) {
		if (right > 0) { 
			if (left > 0) { //normal case, splice in the 'middle' of column
				int[] temp = new int[array[0].length + 2];
				for (int i = 0; i <= index; i ++)
					temp[i] = array[0][i];
				temp[index + 1] = newValue;
				temp[index + 2] = array[0][index];
				for (int i = index + 3; i < temp.length; i++)
					temp[i] = array[0][i - 2];
				int[] temp1 = new int[array[1].length + 2];
				for (int i = 0; i < index; i ++)
					temp1[i] = array[1][i];
				temp1[index] = left;
				temp1[index + 1] = 1;
				temp1[index + 2] = right;
				for (int i = index + 3; i < temp1.length; i++)
					temp1[i] = array[1][i - 2];
				array[0] = temp.clone();
				array[1] = temp1.clone();
			}
			else if (left == 0) { //when we insert at beginning of a column
				int[] temp = new int[array[0].length + 1];
				for (int i = 0; i < index; i ++)
					temp[i] = array[0][i];
				temp[index] = newValue;;
				for (int i = index + 1; i < temp.length; i++)
					temp[i] = array[0][i - 1];
				int[] temp1 = new int[array[1].length + 1];
				for (int i = 0; i < index; i ++)
					temp1[i] = array[1][i];
				temp1[index] = 1;
				temp1[index + 1] = right;
				for (int i = index + 2; i < temp1.length; i++)
					temp1[i] = array[1][i - 1];
				array[0] = temp.clone();
				array[1] = temp1.clone();
				try { //catches exception if nothing is before
					if (array[0][index] == array[0][index - 1])  //checks to see if the section before has same value->merge
						mergeValues(array, index - 1);
				}
				catch (IndexOutOfBoundsException e) {
				}
			}
		}
		else if (right == 0) { //when we insert at the very end of a column 
			if (left > 0) { //when there is a buffer to the left
				int[] temp = new int[array[0].length + 1];
				for (int i = 0; i <= index; i ++)
					temp[i] = array[0][i];
				temp[index + 1] = newValue;;
				for (int i = index + 2; i < temp.length; i++)
					temp[i] = array[0][i - 1];
				int[] temp1 = new int[array[1].length + 1];
				for (int i = 0; i < index; i ++)
					temp1[i] = array[1][i];
				temp1[index + 1] = 1;
				temp1[index] = left;
				for (int i = index + 2; i < temp1.length; i++)
					temp1[i] = array[1][i - 1];	
				array[0] = temp.clone();
				array[1] = temp1.clone();
				try { //catches exception if nothing is after
					if (array[0][index + 1] == array[0][index + 2]) //checks to see if section after has same value->merge
						mergeValues(array, index + 1);
				}
				catch (IndexOutOfBoundsException e) {
				}
			}
			else if (left == 0) { //case when nothing is before or after, ie setting a value at a spot that has count 1
				int[] temp = new int[array[0].length];
				for (int i = 0; i < index; i ++)
					temp[i] = array[0][i];
				temp[index] = newValue;;
				for (int i = index + 1; i < temp.length; i++)
					temp[i] = array[0][i];
				int[] temp1 = new int[array[1].length];
				for (int i = 0; i < index; i ++)
					temp1[i] = array[1][i];
				temp1[index] = 1;
				for (int i = index + 1; i < temp1.length; i++)
					temp1[i] = array[1][i];	
				array[0] = temp.clone();
				array[1] = temp1.clone();
				try { //catches exception if nothing is after
					if (array[0][index] == array[0][index + 1]) //checks to see if section after has same value->merge
						mergeValues(array, index);
				}
				catch (IndexOutOfBoundsException e) {
				}
				try { //catches exception if nothing is before
					if (array[0][index] == array[0][index - 1]) //checks to see if section before has same value->merge
						mergeValues(array, index - 1);
				}
				catch (IndexOutOfBoundsException e) {
				}
				
			}
		}
	}
	
	/**
	 * helper method merges the two columns that are at index and index + 1
	 * @param array: int array
	 * @param index: int
	 */
	private void mergeValues(int[][] array, int index) {
		int[] temp = new int[array[0].length - 1]; //creates new merged array[0]
		for (int i = 0; i < index; i++)
			temp[i] = array[0][i];
		temp[index] = array[0][index];
		for (int i = index + 1; i < temp.length; i++)
			temp[i] = array[0][i + 1];
		int[] temp1 = new int[array[1].length - 1]; //creates new merged array[1]
		for (int i = 0; i < index; i++)
			temp1[i] = array[1][i];
		temp1[index] = array[1][index] + array[1][index + 1];
		for (int i = index + 1; i < temp1.length; i++)
			temp1[i] = array[1][i + 1];
		array[0] = temp.clone();
		array[1] = temp1.clone();
	}
	
	/**
	 * helper method checks to see (if for some reason) an RLESequence in compact form is not simplified and simplifies it
	 */
	private void convertToSimplified() {
		boolean duplicate = true;
		while (duplicate == true) {
			boolean didSomething = false; 
			for (int i = 0; i < RLECompact[0].length - 1; i++) {
				if (RLECompact[0][i] == RLECompact[0][i + 1]) {
					mergeValues(RLECompact, i);
					didSomething = true; // checks if anything happened
					break;
				}		
			}
			if (didSomething == false) // if nothing happened, we are done, otherwise start over to preserve loop integrity/array size change
				duplicate = false;
		}
	}
	
	/**
	 * helper method adjusts the input value based on validity
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
