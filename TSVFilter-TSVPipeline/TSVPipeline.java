import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
/**
 * class directs the program's actions based on the user's filter/compute selections
 * outputs stream (after filter, compute) on a set output file
 * 
 * @author Jesse Zhong, jwz2111
 * @see TSVFilter
 * @see FileChecker
 * @see Person
 * @see Terminal
 *
 */
public class TSVPipeline {
	private TSVFilter myFilter;
	
	/**
	 * constructor uses values from TSVFilter object
	 * @param inFilter: TSVFilter
	 */
	public TSVPipeline(TSVFilter inFilter) {
		myFilter = inFilter;
	}
	
	/**
	 * method does all the bulk work of directing different classes (mainly FileChecker),
	 * writes values to an output file as necessary
	 * 
	 * @throws IOException just in case
	 * 
	 */
	public void doIt() throws IOException{
		String optionValue = myFilter.getOptionValue();
		String optionName = myFilter.getOptionName();
		String computeName = myFilter.getComputeName();
		Terminal terminalName = myFilter.getTerminalName();
		Long optionValueLong = myFilter.getOptionValueLong();
		boolean testALLSAME = true;
		String holdString = "";
		int hold = 0;
		long holdLong = 0;
		Person holdPerson = null;
		int formFlag = 0;
		if (myFilter.getRequired().exists() == false) {
			System.out.println("Input file doesn't exist");
			return;
		}
		Scanner scan = new Scanner(myFilter.getRequired());
		FileChecker myFC = new FileChecker();
		if (scan.hasNextLine() == false) {
			System.out.println("First line doesn't exist");
			scan.close();
			return;
		}
		else {
			myFC.checkFirstLine(scan.nextLine());
		}
		if (scan.hasNextLine() == false) {
			System.out.println("Second line doesn't exist");
			scan.close();
			return;
		}
		else {
			if (myFC.checkSecondLine(scan.nextLine()) == false) {
				scan.close();
				return;
			}
		}
		if (myFC.checkFirstAndSecondLines() == false) {
			scan.close();
			return;
		}
		File outFile = new File("C:\\Users\\jesse\\eclipse-workspace\\Assignment 3\\src\\output.txt"); //adjust for whatever workspace the user is in
		BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
		writer.write(myFC.toString()); //writes first two lines
		if(optionValue == null && optionName == null) { //checks if select() was used or not
			if (computeName == null && terminalName == null) { //if compute not used
				while (scan.hasNextLine()) {
					String temp = scan.nextLine();
					if (myFC.addData(myFC.checkData(temp), temp) == true) //if the String/data value was added after checking validity
						writer.write(myFC.toPerson().toString() + "\n"); //writes to output
					else {
						formFlag = 1; //this means input doesn't have proper form
					}
				}
			}
			else if (terminalName == Terminal.ALLSAME) {
				boolean flagdiff = true;
				int flag = 0;
				if (myFC.computeChecker(computeName) == false) { //checks if compute is valid, flags
					System.out.println("Input compute is not valid");
					flag = 1;
				}
				while (scan.hasNextLine()) {
					String temp = scan.nextLine();
					if (myFC.addData(myFC.checkData(temp), temp) == true && flag == 0) {
						writer.write(myFC.toPerson().toString() + "\n");
						testALLSAME = myFC.computeALLSAME(computeName);
						if (testALLSAME == false) {
							flagdiff = false;
						}
					}
					else {
						formFlag = 1;
					}
				}
				if (flag == 0)
					System.out.println("Compute result for ALLSAME: " + flagdiff); //prints out if compute valid
				else
					System.out.println("Compute result for ALLSAME: "); //prints nothing if compute not valid
			}
			else if (terminalName == Terminal.COUNT) {
				int flag = 0;
				if (myFC.computeChecker(computeName) == false) {
					System.out.println("Input compute is not valid");
					flag = 1;
				}
				while (scan.hasNextLine()) {
					String temp = scan.nextLine();
					if (myFC.addData(myFC.checkData(temp), temp) == true && flag == 0) {
						writer.write(myFC.toPerson().toString() + "\n");
						holdLong = myFC.computeCOUNT(true);
					}
					else {
						formFlag = 1;
					}
				}
				System.out.println("Compute result for COUNT: " + holdLong);
			}
			else if (terminalName == Terminal.MIN) {
				int flag = 0;
				if (myFC.computeChecker(computeName) == false) {
					System.out.println("Input compute is not valid");
					flag = 1;
				}
				while (scan.hasNextLine()) {
					String temp = scan.nextLine();
					if (myFC.addData(myFC.checkData(temp), temp) == true && flag == 0) {
						writer.write(myFC.toPerson().toString() + "\n");
						holdString = myFC.computeMIN(computeName);
					}
					else {
						formFlag = 1;
					}
				}
				System.out.println("Compute result for MIN: " + holdString);
			}
			else if (terminalName == Terminal.MAX) {
				int flag = 0;
				if (myFC.computeChecker(computeName) == false) {
					System.out.println("Input compute is not valid");
					flag = 1;
				}
				while (scan.hasNextLine()) {
					String temp = scan.nextLine();
					if (myFC.addData(myFC.checkData(temp), temp) == true && flag == 0) {
						writer.write(myFC.toPerson().toString() + "\n");
						holdString = myFC.computeMAX(computeName);
					}
					else {
						formFlag = 1;
					}
				}
				System.out.println("Compute result for MAX: " + holdString);
			}
			else if (terminalName == Terminal.SUM) { //will print 0 if it is a sum of Strings
				int flag = 0;
				if (myFC.computeChecker(computeName) == false) {
					System.out.println("Input compute is not valid");
					flag = 1;
				}
				while (scan.hasNextLine()) {
					String temp = scan.nextLine();
					if (myFC.addData(myFC.checkData(temp), temp) == true && flag == 0) {
						writer.write(myFC.toPerson().toString() + "\n");
						holdLong = myFC.computeSUM(computeName);
					}
					else {
						formFlag = 1;
					}
				}
				if (flag == 0)
					System.out.println("Compute result for SUM: " + holdLong);
				else
					System.out.println("Compute result for SUM: ");
					
			}
			else if (terminalName == Terminal.FIRSTDIFF) { 
				int flag = 0;
				int flagPerson = 0; //checks if person has been found
				if (myFC.computeChecker(computeName) == false) {
					System.out.println("Input compute is not valid");
					flag = 1;
				}
				while (scan.hasNextLine()) {
					String temp = scan.nextLine();
					if (myFC.addData(myFC.checkData(temp), temp) == true && flag == 0) {
						writer.write(myFC.toPerson().toString() + "\n");
						if (flagPerson == 0) { //if a person who is different has not been found
							holdPerson = myFC.computeFIRSTDIFF(computeName);
							if (holdPerson != null) { //if a person has just been found
								flagPerson = 1;
								hold++;
							}
						}
						else { //starts to count when a person has been found
							if (myFC.checkPerson(holdPerson, computeName) == true) {
								hold++;
							}
						}
					}
					else {
						formFlag = 1;
					}
				}
				if (flag == 0 && flagPerson == 1) //when person exists
					System.out.println("Compute result for FIRSTDIFF: (" + holdPerson.toString() + "), found same value " + hold + " time(s)");
				else if (flag == 0 && flagPerson == 0) //when holdPerson doesn't exist (null)
					System.out.println("Compute result for FIRSTDIFF: found no differing value");
				else
					System.out.println("Compute result for FIRSTDIFF: ");
					
			}
		}
		else { //if filter/select() was used
			if (myFC.FilterChecker(optionName, optionValue, optionValueLong) == true) { //if filter is even valid
				if (computeName == null && terminalName == null) {
					while (scan.hasNextLine()) {
						String temp = scan.nextLine();
						if (myFC.addDataWithFilter(myFC.checkData(temp), temp, optionName, optionValue) == true) { //if select() was used
							writer.write(myFC.toPerson().toString() + "\n");
						}
						else {
							formFlag = 1;
						}
					}
				}
				else if (terminalName == Terminal.ALLSAME) {
					boolean flagdiff = true;
					int flag = 0;
					if (myFC.computeChecker(computeName) == false) { //checks if compute is valid, flags
						System.out.println("Input compute is not valid");
						flag = 1;
					}
					while (scan.hasNextLine()) {
						String temp = scan.nextLine();
						if (myFC.addDataWithFilter(myFC.checkData(temp), temp, optionName, optionValue) == true && flag == 0) {
							writer.write(myFC.toPerson().toString() + "\n");
							testALLSAME = myFC.computeALLSAME(computeName);
							if (testALLSAME == false) {
								flagdiff = false;
							}
						}
						else {
							formFlag = 1;
						}
					}
					if (flag == 0)
						System.out.println("Compute result for ALLSAME: " + flagdiff); //prints out if compute valid
					else
						System.out.println("Compute result for ALLSAME: "); //prints nothing if compute not valid
				}
				else if (terminalName == Terminal.COUNT) {
					int flag = 0;
					if (myFC.computeChecker(computeName) == false) {
						System.out.println("Input compute is not valid");
						flag = 1;
					}
					while (scan.hasNextLine()) {
						String temp = scan.nextLine();
						if (myFC.addDataWithFilter(myFC.checkData(temp), temp, optionName, optionValue) == true && flag == 0) {
							writer.write(myFC.toPerson().toString() + "\n");
							holdLong = myFC.computeCOUNT(true);
						}
						else {
							formFlag = 1;
						}
					}
					System.out.println("Compute result for COUNT: " + holdLong);
				}
				else if (terminalName == Terminal.MIN) {
					int flag = 0;
					if (myFC.computeChecker(computeName) == false) {
						System.out.println("Input compute is not valid");
						flag = 1;
					}
					while (scan.hasNextLine()) {
						String temp = scan.nextLine();
						if (myFC.addDataWithFilter(myFC.checkData(temp), temp, optionName, optionValue) == true && flag == 0) {
							writer.write(myFC.toPerson().toString() + "\n");
							holdString = myFC.computeMIN(computeName);
						}
					}
					System.out.println("Compute result for MIN: " + holdString);
				}
				else if (terminalName == Terminal.MAX) {
					int flag = 0;
					if (myFC.computeChecker(computeName) == false) {
						System.out.println("Input compute is not valid");
						flag = 1;
					}
					while (scan.hasNextLine()) {
						String temp = scan.nextLine();
						if (myFC.addDataWithFilter(myFC.checkData(temp), temp, optionName, optionValue) == true && flag == 0) {
							writer.write(myFC.toPerson().toString() + "\n");
							holdString = myFC.computeMAX(computeName);
						}
						else {
							formFlag = 1;
						}
					}
					System.out.println("Compute result for MAX: " + holdString);
				}
				else if (terminalName == Terminal.SUM) { //will print 0 if it is a sum of Strings
					int flag = 0;
					if (myFC.computeChecker(computeName) == false) {
						System.out.println("Input compute is not valid");
						flag = 1;
					}
					while (scan.hasNextLine()) {
						String temp = scan.nextLine();
						if (myFC.addDataWithFilter(myFC.checkData(temp), temp, optionName, optionValue) == true && flag == 0) {
							writer.write(myFC.toPerson().toString() + "\n");
							holdLong = myFC.computeSUM(computeName);
						}
						else {
							formFlag = 1;
						}
					}
					if (flag == 0)
						System.out.println("Compute result for SUM: " + holdLong);
					else
						System.out.println("Compute result for SUM: ");
						
				}
				else if (terminalName == Terminal.FIRSTDIFF) { //will print 0 if it is a sum of Strings
					int flag = 0;
					int flagPerson = 0; //checks if person has been found
					if (myFC.computeChecker(computeName) == false) {
						System.out.println("Input compute is not valid");
						flag = 1;
					}
					while (scan.hasNextLine()) {
						String temp = scan.nextLine();
						if (myFC.addDataWithFilter(myFC.checkData(temp), temp, optionName, optionValue) == true && flag == 0) {
							writer.write(myFC.toPerson().toString() + "\n");
							if (flagPerson == 0) { //if a person who is different has not been found
								holdPerson = myFC.computeFIRSTDIFF(computeName);
								if (holdPerson != null) { //if a person has just been found
									flagPerson = 1;
									hold++;
								}
							}
							else { //starts to count when a person has been found
								if (myFC.checkPerson(holdPerson, computeName) == true) {
									hold++;
								}
							}
						}
						else {
							formFlag = 1;
						}
					}
					if (flag == 0 && flagPerson == 1) //when person exists
						System.out.println("Compute result for FIRSTDIFF: (" + holdPerson.toString() + "), found same value " + hold + " time(s)");
					else if (flag == 0 && flagPerson == 0) //when holdPerson doesn't exist (null)
						System.out.println("Compute result for FIRSTDIFF: found no differing value");
					else
						System.out.println("Compute result for FIRSTDIFF: ");
						
				}
			}
			else { //if filter isn't valid
				System.out.println("Input filter/select is not valid");
				scan.close();
				writer.close();
				return;
			}
		}
		scan.close();
		writer.close();
		System.out.println("Copied all valid data entries (with filter) into new output.txt file");
		if (formFlag == 1)
			System.out.println("Input file doesn't have proper form"); //checks for proper form based on formFlag
		else
			System.out.println("Input file has proper form");
	}
}
