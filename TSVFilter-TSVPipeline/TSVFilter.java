import java.io.File;
/**
 * This class allows the user to utilize various filter select and terminal compute methods through 
 * a builder pattern with enclosed class Info
 * 
 * @author Jesse Zhong, jwz2111
 * @see Info
 * @see Terminal
 *
 */
public class TSVFilter {
	
	private final File required;
	private final String optionName;
	private final String optionValue;
	private final String computeName;
	private final Terminal terminalName;
	private final Long optionValueLong;
	
	//private constructor sets values based on Info
	private TSVFilter(Info inInfo) {
		required = inInfo.required;
		optionName = inInfo.optionName;
		optionValue = inInfo.optionValue;
		computeName = inInfo.computeName;
		terminalName = inInfo.terminalName;
		optionValueLong = inInfo.optionValueLong;
	}
	
	/**
	 * 
	 * @return File
	 */
	public File getRequired() {
		return required;
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getOptionName() {
		return optionName;
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getOptionValue() {
		return optionValue;
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getComputeName() {
		return computeName;
	}
	
	/**
	 * 
	 * @return Terminal
	 */
	public Terminal getTerminalName() {
		return terminalName;
	}
	
	/**
	 * 
	 * @return Long
	 */
	public Long getOptionValueLong() {
		return optionValueLong;
	}
	
	/**
	 * returns the filters/computes currently being used in a String
	 */
	public String toString() {
		if (terminalName == null) {
			return "Using TSVFilter with file: " + required.toString() + " and select: (" + optionName + ", " + optionValue + ") and compute: (" + computeName + ", " + "null" + ")";
		}
		else {
			return "Using TSVFilter with file: " + required.toString() + " and select: (" + optionName + ", " + optionValue + ") and compute: (" + computeName + ", Terminal." + terminalName.toString() + ")";
		}
	}
	
	/**
	 * nested class allows user to set select/compute methods in a builder pattern
	 * 
	 * @author Jesse Zhong, jwz2111
	 * 
	 * @see TSVFilter
	 * @see Terminal
	 */
	public static class Info{
		
		/**
		 * constructor takes in the input file
		 * 
		 * @param inRequired: File
		 * 
		 */
		public Info(File inRequired) {
			required = inRequired;
		}
		
		/**
		 * 
		 * @param inOptionName: String
		 * @param inOptionValue: String
		 * @return Info
		 */
		public Info select(String inOptionName, String inOptionValue) {
			optionName = inOptionName;
			optionValue = inOptionValue;
			return this;
		}
		
		/**
		 * 
		 * @param inOptionName: String
		 * @param inOptionValue: long
		 * @return Info
		 */
		public Info select(String inOptionName, long inOptionValue) { //accounts for data entered as a long
			optionName = inOptionName;
			optionValue = Long.toString(inOptionValue);
			optionValueLong = Long.valueOf(inOptionValue);
			return this;
		}
		
		/**
		 * 
		 * @param inOptionName: String
		 * @param inOptionValue: String
		 * @return Info
		 */
		public Info outlier(String inOptionName, String inOptionValue) {
			optionName = inOptionName;
			optionValue = inOptionValue;
			return this;
		}
		
		/**
		 * 
		 * @param inOptionName: String
		 * @param inOptionValue: long
		 * @return Info
		 */
		public Info outlier(String inOptionName, long inOptionValue) { //accounts for data entered as a long
			optionName = inOptionName;
			optionValue = Long.toString(inOptionValue);
			optionValueLong = Long.valueOf(inOptionValue);
			return this;
		}
		
		/**
		 * 
		 * @param inComputeName: String
		 * @param inTerminalName: Terminal
		 * @return Info
		 */
		public Info compute(String inComputeName, Terminal inTerminalName) {
			computeName = inComputeName;
			terminalName = inTerminalName;
			return this;
		}
		
		/**
		 * completes the user's selections, creates TSVFilter anonymously
		 * 
		 * @return TSVFilter
		 * 
		 */
		public TSVFilter done() {
			return new TSVFilter(this);
		}
		
		private final File required;
		private String optionName;
		private String optionValue;
		private String computeName;
		private Terminal terminalName;
		private Long optionValueLong;
	}
}
