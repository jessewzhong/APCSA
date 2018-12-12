import java.io.*;
/**
 * This class allows the user to specify which file to use that we are streaming data
 * over and allows the selection of a select/outlier method and/or a terminal compute method
 * 
 * The user is able to input the parameters for the filter methods as well as comment 
 * them out if not used
 * 
 * @author Jesse Zhong, jwz2111
 * 
 * @see TSVFilter
 * @see TSVPipeline
 * @see Terminal
 *
 */
public class Runner {

	public static void main(String[] args) throws IOException {
		File inFile = new File("C:\\Users\\jesse\\eclipse-workspace\\Assignment 3\\src\\input.txt"); //adjust for whatever workspace user is in
		TSVFilter myTSVFilter = new TSVFilter
				.Info(inFile)
				.outlier("Age", 1) //use if user wants filter select/outlier, comment out if not
				.compute("Age", Terminal.SUM) //use if user wants to compute, comment out if not
				.done();
		System.out.println(myTSVFilter); //prints what select/compute user is using
		new TSVPipeline(myTSVFilter).doIt(); //starts streaming
	}
}
