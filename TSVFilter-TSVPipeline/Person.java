import java.util.ArrayList;
/**
 * class stores all the information for a person/data's field values and types
 * 
 * @author Jesse Zhong, jwz2111
 */
public class Person {
	private ArrayList<String> stringValues;
	private ArrayList<Long> longValues;
	private ArrayList<String> types;
	
	/**
	 * initializes person and ArrayLists
	 */
	public Person() {
		stringValues = new ArrayList<String>();
		longValues = new ArrayList<Long>();
		types = new ArrayList<String>();
	}
	
	/**
	 * adds new value to stringValues
	 * 
	 * @param inString: String
	 * 
	 */
	public void addString(String inString) {
		stringValues.add(inString);
	}
	
	/**
	 * adds new value to longValues
	 * 
	 * @param inLong: Long
	 */
	public void addLong(Long inLong) {
		longValues.add(inLong);
	}
	
	/**
	 * adds new value to types
	 * 
	 * @param inString: String
	 * 
	 */
	public void addTypes(String inString) {
		types.add(inString);
	}
	
	/**
	 * returns ArrayList of Person's Long values
	 * 
	 * @return ArrayList
	 */
	public ArrayList<Long> getLongValues() {
		return longValues;
	}
	
	/**
	 * returns ArrayList of Person's String values
	 * 
	 * @return ArrayList
	 * 
	 */
	public ArrayList<String> getStringValues() {
		return stringValues;
	}
	
	/**
	 * returns the standard format for a Person
	 */
	public String toString() {
		String temp = "";
		int stringCount = 0;
		int longCount = 0;
		for (int i = 0; i < types.size(); i++) {
			if (types.get(i).equals("String")) {
				temp += stringValues.get(stringCount) + "\t";
				stringCount++;
			}
			if (types.get(i).equals("long")) {
				temp += longValues.get(longCount) + "\t";
				longCount++;
			}
		}
		if (temp.length() != 0) //in case input is all new lines
			temp = temp.substring(0,temp.length() - 1); //take out last tab
		return temp;
	}
}
