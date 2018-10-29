/**
 * @author Jesse Zhong, jwz2111
 * class helps to convert forms of the expanded and compact RLESequences
 * converts from array to 2D array, vice versa
 *
 */
public class RLEConverter {

	public RLEConverter() {
		
	}

	/**
	 * static method converts from 1D int array to 2D int array compact form
	 * @param sequence: int array
	 * @return int 2D array
	 */
	public static int[][] toCompact(int[] sequence) {
		int[][] VC = new int[2][0];
		try {
			int value = sequence[0];
			int count = 0;
			for (int i = 0; i < sequence.length; i++) { //iterates
				if (sequence[i] == value) { //if current sequence value is the same as previous, keep going
					count++;
				}
				else {
					appendValues(VC, value, count); //if current sequence value is different, add new value and count of previous element(s) to array
					value = sequence[i];
					count = 1;
				}
			}
			appendValues(VC, value, count); //append at the end
		}
		catch (IndexOutOfBoundsException e) { //catches error if empty row
			return VC;
		}
		return VC;
	}
	

	/**
	 * static method converts from compact 2D form to expanded 1D array form
	 * @param sequence: 2D int array
	 * @return 1D int array
	 */
	public static int[] toSpaced(int[][] sequence) {
		int[] temp = new int[0];
		for (int i = 0; i < sequence[0].length; i++) { //iterates through values
			for (int j = 0; j < sequence[1][i]; j++) { //counts how many times value needs to be added
				int[] temp1 = new int[temp.length + 1];
				for (int k = 0; k < temp.length; k++)
					temp1[k] = temp[k];
				temp1[temp1.length - 1] = sequence[0][i];
				temp = temp1.clone();
			}
		}
		return temp;
	}
	
	/**
	 * helper method that adds value1 to array[0] at the end and value2 to array[1] at the end
	 * @param array: 2D int array
	 * @param value1: int
	 * @param value2: int
	 */
	private static void appendValues(int[][] array, int value1, int value2) {
		int[] temp1 = new int[array[0].length + 1]; //for array[0]
		for (int i = 0; i < array[0].length; i++)
			temp1[i] = array[0][i];
		temp1[temp1.length - 1] = value1;
		array[0] = temp1.clone();
		int[] temp2 = new int[array[1].length + 1]; //for array[1]
		for (int i = 0; i < array[1].length; i++)
			temp2[i] = array[1][i];
		temp2[temp2.length - 1] = value2;
		array[1] = temp2.clone();
	}
	
	/*public static int[] toSpaced(int[][] sequence) {
		int[] temp = new int[0];
		for (int i = 0; i < sequence[0].length; i++) {
			for (int j = 0; j < sequence[1][i]; j++) {
				appendValue(temp, sequence[0][i]);
			}
		}
		return temp;
	}*/
	
	
	/*private static void appendValue(int[] array, int value) {
		int[] temp = new int[array.length + 1];
		for (int i = 0; i < array.length; i++)
			temp[i] = array[i];
		temp[temp.length - 1] = value;
		array = temp.clone();
	}
	

	private static void appendValues(int[][] array, int value1, int value2) {
		appendValue(array[0], value1);
		appendValue(array[1], value2);
	}
	*/
	
}
