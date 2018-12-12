import java.util.ArrayList;
/**
 * class contains all the methods and fields to perform all the filter/select/outlier as well as compute methods,
 * holds currentPerson, etc. to perform some methods, but takes data along with the stream
 * 
 * @author Jesse Zhong, jwz2111
 * 
 * @see Person
 */
public class FileChecker {
	private ArrayList<String> fields;
	private ArrayList<String> types;
	private String holdString;
	private Long holdLong;
	private long holdlong;
	private Person currentPerson; //current data line being added
	private Person previousPerson; //previous data line added
	private Person currentInPerson; //current data line in input file, may not satisfy filter
	private int outlierFlag;
	
	/**
	 * constructor initializes ArrayLists
	 */
	public FileChecker() {
		fields = new ArrayList<String>();
		types = new ArrayList<String>();
	}

	/**
	 * takes in a string and parses it into fields, adding them into the ArrayList fields
	 * 
	 * @param temp: String
	 * 
	 */
	public void checkFirstLine(String temp) {
		ArrayList<String> tempFields = new ArrayList<String>();
		while (temp.indexOf('\t') != -1) {
			tempFields.add(temp.substring(0, temp.indexOf('\t')));
			temp = temp.substring(temp.indexOf('\t') + 1);
			while(temp.indexOf('\t') == 0) { //if next part of string starts with a tab(s)
				temp = temp.substring(temp.indexOf('\t') + 1);
			}
		}
		if (temp.length() != 0) //in case string ended with all tabs
			tempFields.add(temp);
		fields = tempFields;
		System.out.println("These are the data fields entered:");
		for (String s : fields) //prints out fields
			System.out.println(s);
	}
	
	/**
	 * checks if the entries in the second line are valid (String or long) and parses
	 * them into the ArrayList types
	 * 
	 * @param temp: String
	 * @return boolean
	 * 
	 */
	public boolean checkSecondLine(String temp) {
		ArrayList<String> tempTypes = new ArrayList<String>();
		while (temp.indexOf('\t') != -1) {
			if (temp.substring(0, temp.indexOf('\t')).equals("String") || temp.substring(0, temp.indexOf('\t')).equals("long")) {
				tempTypes.add(temp.substring(0, temp.indexOf('\t')));
				temp = temp.substring(temp.indexOf('\t') + 1);
				while(temp.indexOf('\t') == 0) { //if next part of string starts with a tab(s)
					temp = temp.substring(temp.indexOf('\t') + 1);
				}
			}
			else {
				System.out.println("Input data types not valid");;
				return false;
			}
		}
		if (temp.length() != 0) { //in case string ended with all tabs
			if (temp.equals("String") || temp.equals("long")) {
				tempTypes.add(temp);
			}
			else {
				System.out.println("Input data types not valid");
				return false;
			}
		}
		types = tempTypes;
		System.out.println("These are the data types entered:");
		for (String s : types) //prints out types
			System.out.println(s);
		return true;
	}
	
	
	/**
	 * method checks if the first and second lines are matching, i.e. number of arguments is the same
	 * 
	 * @return boolean
	 * 
	 */
	public boolean checkFirstAndSecondLines() {
		if (types.size() != fields.size()) {
			System.out.println("First and second line don't match in number of entries");
			return false;
		}
		return true;
	}
	
	/**
	 * checks to see if the input data from the stream corresponds to the correct number of arguments
	 * 
	 * @param temp: String
	 * @return boolean
	 * 
	 */
	public boolean checkData(String temp) {
		int sizeCheck = 0;
		while (temp.indexOf('\t') != -1) { //gets size of next line
			sizeCheck++;
			temp = temp.substring(temp.indexOf('\t') + 1);
			while(temp.indexOf('\t') == 0) { //if next part of string starts with a tab(s)
				temp = temp.substring(temp.indexOf('\t') + 1);
			}	
		}
		if (temp.length() != 0) //in case string ended with all tabs
			sizeCheck++;
		if (sizeCheck == types.size()) { //checks if next line has same size
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * adds the data from the given String if it meets all requirements, updates currentPerson 
	 * and previousPerson 
	 * 
	 * @param b: boolean
	 * @param temp: String
	 * @return boolean
	 * 
	 */
	public boolean addData(boolean b, String temp) {
		if (b == true) { //checks if next line has same size
			Person p = addSingleData(temp);
			if (p != null) { //makes sure the next line has proper data types
				previousPerson = currentPerson;
				currentPerson = p;
				return true;
			}
		}
		return false;
	}
	/**
	 * adds the data from the given String if it meets all requirements (also of filter), updates
	 * currentPerson, previousPerson, and currentInPerson
	 * 
	 * @param b: boolean
	 * @param temp: String
	 * @param inName: String
	 * @param inValue: String
	 * @return boolean
	 * 
	 */
	public boolean addDataWithFilter(boolean b, String temp, String inName, String inValue) {
			if (b == true) { //checks if next line has same size
				Person p = addSingleData(temp);
				if (p != null && PersonFilterChecker(p, inName, inValue) == true) {//makes sure the next line has proper data types
					previousPerson = currentPerson;
					currentInPerson = p;
					currentPerson = p;
					return true;
				}
				if (p != null) //updates currentInperson even if person doesn't satisfy filter to check outlier
					currentInPerson = p;
			}
			return false;
	}
	
	 //updates the fields of the Person object while checking if data values of the String are valid,
	 //returns a null object if not valid, otherwise the Person
	private Person addSingleData(String inString) { //adds long and String data to a person, keeping order relatively known
		Person p = new Person();
		for (int i = 0; i < types.size(); i++) { //assumes inString has the desired number of data entries
			if (types.get(i).equals("long")) {
				try {
					if (inString.indexOf('\t') != -1) {
						p.addLong(Long.parseLong(inString.substring(0, inString.indexOf('\t'))));
					}
					else 
						p.addLong(Long.parseLong(inString));
					p.addTypes("long");
				}
				catch(NumberFormatException e) {
					return null;
				}
			}
			else {
				if (inString.indexOf('\t') != -1)
					p.addString(inString.substring(0, inString.indexOf('\t')));
				else
					p.addString(inString);
				p.addTypes("String");
			}
			if(inString.indexOf('\t') != -1) { //takes care of multiple tabs in a row
				inString = inString.substring(inString.indexOf('\t') + 1);
				while(inString.indexOf('\t') == 0) { //if next part of string starts with a tab(s)
					inString = inString.substring(inString.indexOf('\t') + 1);
				}
			}
		}
		return p;
	}
	
	/**
	 * This method checks if the given filter is valid or not, returns true/false
	 * 
	 * @param name: String
	 * @param value: String
	 * @param valueLong: Long
	 * @return boolean
	 * 
	 */
	public boolean FilterChecker(String name, String value, Long valueLong) {
		if (fields.indexOf(name) == -1)
			return false;
		else {
			if (types.get(fields.indexOf(name)).equals("long")) { //checks if value is of long type
				try {
					if(valueLong == null)
						return false;
					return true;
				}
				catch(NumberFormatException e) {
					return false;
				}
			}
			else {
				if (valueLong == null) { //checks if String selectName has long selectValue
					return true;
				}
				else
					return false;
			}
		}
	}
	
	

	/*
	//regular select filter
	//helper method checks if given Person satisfies filter requirements
	private boolean PersonFilterChecker(Person inPerson, String name, String value) {
		int index = fields.indexOf(name); //where desired name is in list of fields
		int longCount = 0;
		int stringCount = 0;
		for (int i = 0; i <= index; i++) {
			if (types.get(i).equals("long")) {
				longCount++;
			}
			else
				stringCount++;		
		}
		if (types.get(index).equals("long")) { //if data type of name is long
			if (Long.valueOf(value).equals(inPerson.getLongValues().get(longCount - 1))) {
				return true;
			}
			else
				return false;
		}
		else { //if data type of name is String
			if (value.equals(inPerson.getStringValues().get(stringCount - 1))) {
				return true;
			}
			else
				return false;
		}
	}
	*/
	
	
	
	//outlier filter
	//helper method checks if given Person satisfies filter requirements
	public boolean PersonFilterChecker(Person inPerson, String name, String value) {
		int index = fields.indexOf(name); //where desired name is in list of fields
		int longCount = 0;
		for (int i = 0; i <= index; i++) {
			if (types.get(i).equals("long")) { //
				longCount++;
			}	
		}
		try {
			long temp = Long.parseLong(value);
			if (currentInPerson == null) //if no data yet
				return true;
			else {
				long a = inPerson.getLongValues().get(longCount - 1).longValue();
				long b = currentInPerson.getLongValues().get(longCount - 1).longValue(); //uses last input value, even if not past filter
				if (a - b > temp || b - a > temp) {
					return true;
				}
				else
					return false;
			}
		}
		catch(Exception e) {
			if (outlierFlag == 0) {
				System.out.println("Outlier arguments don't make sense");
				outlierFlag++;
			}
			return false;
		}
	}
	
	/**
	 * checks to see if computeName is valid
	 * 
	 * @param name: String
	 * @return boolean
	 * 
	 */
	public boolean computeChecker(String name) {
		if (fields.indexOf(name) == -1)
			return false;
		return true;
	}
	
	/**
	 * does the compute method for Terminal.ALLSAME
	 * 
	 * @param inName: String
	 * @return boolean
	 * 
	 */
	public boolean computeALLSAME(String inName) {
		int index = fields.indexOf(inName);
		int longCount = 0;
		int stringCount = 0;
		for (int i = 0; i <= index; i++) {
			if (types.get(i).equals("long")) {
				longCount++;
			}
			else
				stringCount++;		
		}
		if (types.get(index).equals("long")) {
			if(previousPerson == null) //checks if there is something to compare to
				return true;
			else if (!currentPerson.getLongValues().get(longCount - 1).equals(previousPerson.getLongValues().get(longCount - 1))) {
				return false;
			}
		}
		else {
			if (previousPerson == null)
				return true;
			else if (!currentPerson.getStringValues().get(stringCount - 1).equals(previousPerson.getStringValues().get(stringCount - 1))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * does the compute method for Terminal.COUNT
	 * 
	 * @param b: boolean
	 * @return long
	 * 
	 */
	public long computeCOUNT(boolean b) {
		if (b == true)
			holdlong++;
		return holdlong;
		
	}
	
	/**
	 * does the compute method for Terminal.MIN
	 * 
	 * @param inName: String
	 * @return String
	 * 
	 */
	public String computeMIN(String inName) {
		int index = fields.indexOf(inName);
		int longCount = 0;
		int stringCount = 0;
		for (int i = 0; i <= index; i++) {
			if (types.get(i).equals("long")) {
				longCount++;
			}
			else
				stringCount++;		
		}
		if (types.get(index).equals("long")) {
			if (holdLong == null) { //if nothing yet
				holdLong = currentPerson.getLongValues().get(longCount - 1);
				return holdLong.toString();
			}
			else {
				if (currentPerson.getLongValues().get(longCount - 1).compareTo(holdLong) < 0) {
					holdLong = currentPerson.getLongValues().get(longCount - 1);
					return holdLong.toString();
				}
				else {
					return holdLong.toString();
				}
			}
		}
		else {
			if (holdString == null) {
				holdString = currentPerson.getStringValues().get(stringCount - 1);
				return holdString;
			}
			else {
				if (currentPerson.getStringValues().get(stringCount - 1).compareTo(holdString) < 0) {
					holdString = currentPerson.getStringValues().get(stringCount - 1);
					return holdString;
				}
				else {
					return holdString;
				}
			}
		}
		
	}
	
	/**
	 * does the compute method for Terminal.MAX
	 * 
	 * @param inName: String
	 * @return String
	 */
	public String computeMAX(String inName) {
		int index = fields.indexOf(inName);
		int longCount = 0;
		int stringCount = 0;
		for (int i = 0; i <= index; i++) {
			if (types.get(i).equals("long")) {
				longCount++;
			}
			else
				stringCount++;		
		}
		if (types.get(index).equals("long")) {
			if (holdLong == null) {
				holdLong = currentPerson.getLongValues().get(longCount - 1);
				return holdLong.toString();
			}
			else {
				if (currentPerson.getLongValues().get(longCount - 1).compareTo(holdLong) > 0) {
					holdLong = currentPerson.getLongValues().get(longCount - 1);
					return holdLong.toString();
				}
				else {
					return holdLong.toString();
				}
			}
		}
		else {
			if (holdString == null) {
				holdString = currentPerson.getStringValues().get(stringCount - 1);
				return holdString;
			}
			else {
				if (currentPerson.getStringValues().get(stringCount - 1).compareTo(holdString) > 0) {
					holdString = currentPerson.getStringValues().get(stringCount - 1);
					return holdString;
				}
				else {
					return holdString;
				}
			}
		}
		
	}
	
	/**
	 * does the compute method for Terminal.SUM
	 * 
	 * @param inName: String
	 * @return long
	 * 
	 */
	public long computeSUM(String inName) {
		if (types.get(fields.indexOf(inName)).equals("String")) //returns 0 if summing Strings
			return 0;
		else {
			int index = fields.indexOf(inName);
			int longCount = 0;
			for (int i = 0; i <= index; i++) {
				if (types.get(i).equals("long")) {
					longCount++;
				}	
			}
			holdlong += currentPerson.getLongValues().get(longCount - 1).longValue();
			return holdlong;
		}
	}
	
	/**
	 * does the compute method for Terminal.FIRSTDIFF, returns first Person that is different
	 * 
	 * @param inName: String
	 * @return Person
	 * 
	 */
	public Person computeFIRSTDIFF(String inName) {
		int index = fields.indexOf(inName);
		int longCount = 0;
		int stringCount = 0;
		for (int i = 0; i <= index; i++) {
			if (types.get(i).equals("long")) {
				longCount++;
			}
			else
				stringCount++;		
		}
		if (previousPerson == null) //consider case of just one entry
			return null;
		if (types.get(index).equals("long")) {
			if (!currentPerson.getLongValues().get(longCount - 1).equals(previousPerson.getLongValues().get(longCount - 1)))
				return currentPerson;
		}
		else {
			if (!currentPerson.getStringValues().get(stringCount - 1).equals(previousPerson.getStringValues().get(stringCount - 1)))
				return currentPerson;
		}
		return null;
	}
	
	/**
	 * 
	 * method checks to see if current Person being added is the same as inPerson (used for FIRSTDIFF compute)
	 * to see if we need to increment the value by 1 if they match
	 * 
	 * @param inPerson: Person
	 * @param inName: String
	 * @return boolean
	 * 
	 */
	public boolean checkPerson(Person inPerson, String inName) {
		int index = fields.indexOf(inName);
		int longCount = 0;
		int stringCount = 0;
		for (int i = 0; i <= index; i++) {
			if (types.get(i).equals("long")) {
				longCount++;
			}
			else
				stringCount++;		
		}
		if (types.get(index).equals("long")) {
			if (currentPerson.getLongValues().get(longCount - 1).equals(inPerson.getLongValues().get(longCount - 1)))
				return true;
		}
		else {
			if (currentPerson.getStringValues().get(stringCount - 1).equals(inPerson.getStringValues().get(stringCount - 1)))
				return true;
		}
		return false;
	}
	
	/**
	 * returns the current person
	 * 
	 * @return Person
	 * 
	 */
	public Person toPerson() {
		return currentPerson;
	}


	/**
	 * toString method to print the first two lines
	 */
	public String toString() {
		String temp = "";
		for (String s : fields) {
			temp += s + "\t";
		}
		if (temp.length() != 0) //in case length is 0
			temp = temp.substring(0, temp.length() - 1); //get rid of last tab
		temp += "\n";
		for (String s : types) {
			temp += s + "\t";
		}
		if (temp.length() != 1) //in case length is 0
			temp = temp.substring(0, temp.length() - 1); //get rid of last tab
		temp += "\n";
		return temp;
	}
}
