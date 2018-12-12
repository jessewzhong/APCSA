import java.awt.*;
import java.awt.geom.*;
/**
 * Class represents the "leaf" object of the composite pattern for MovingObject,
 * specifying the methods for this particular Unicycle class, see JRK's file online
 * 
 * @author Jesse Zhong, jwz2111, with help from JRK
 *
 */
public class Unicycle implements MovingObject {
	
	private int x;
	private int y;
	private int unit;
	private int status; //for the pedals, how much TOTAL linear distance traveled
	private final double SEATHEIGHT = 0.25;
	private final double RODHEIGHT = 2.0;
	private final double WHEELSIZE = 2.0;
	private final double HOLESIZE = 0.25;
	private final double PEDALSIZE = 1.5;
	private final double STEPSIZE = 0.5;
	private final double STEPHEIGHT = 0.125;
	
	/**
	 * Constructor initializes a Unicycle with anchor coordinates and a unit standard for comparisons
	 * 
	 * @param x anchor x-coordinate int
	 * @param y anchor y-coordinate int
	 * @param unit unit standard as an int
	 */
	public Unicycle(int x, int y, int unit) {
		this.x = x;
		this.y = y;
		this.unit = unit; //unit in pixels
	}
	
	/**
	 * Method translates the Unicycle by altering the anchor point, also updates status
	 * in order to keep track of total distance traveled
	 */
	public void translate (int xChange, int yChange) {
		/*if (x < Tester.ICON_W + (int)((WHEELSIZE / 2 - 1.0 / 2) * unit)) {
			x += xChange;
		}
		else if (x == Tester.ICON_W + (int)((WHEELSIZE / 2 - 1.0 / 2) * unit)) {
			x = - (int)((WHEELSIZE / 2 + 1.0 / 2) * unit);
		}
		*/
		x += xChange;
		x = Math.floorMod(x, Tester.ICON_W);
		y += yChange;
		status += xChange;
	}
	
	/**
	 * Method draws the Unicycle based on given constants and relative sizes
	 */
	public void draw(Graphics2D g2D) {
		//basic x symmetry
		int xCenter = x + unit / 2;
		
		//seat
		int seatXUpLeft = x;
		int seatYUpLeft = y;
		int seatLength = unit;
		int seatHeight = (int) (SEATHEIGHT * unit);
		Rectangle2D.Double seat = new Rectangle2D.Double(seatXUpLeft,  seatYUpLeft,  seatLength,  seatHeight);;
		int seatYBot = seatYUpLeft + seatHeight;
		
		//rod
		int rodXTop = xCenter;
		int rodYTop = seatYBot;
		int rodXBot = xCenter;
		int rodYBot = rodYTop + (int)(RODHEIGHT * unit);
		Line2D.Double rod = new Line2D.Double(rodXTop, rodYTop, rodXBot, rodYBot);
		
		//hole
		int holeXUpLeft = rodXBot - (int)(HOLESIZE * unit / 2);
		int holeYUpLeft = rodYBot - (int)(HOLESIZE * unit / 2);
		int holeSize = (int)(HOLESIZE * unit);
		Ellipse2D.Double hole = new Ellipse2D.Double(holeXUpLeft,  holeYUpLeft, holeSize, holeSize);
		
		//wheel
		int wheelXUpLeft = rodXBot - (int)(WHEELSIZE * unit /2);
		int wheelYUpLeft = rodYBot - (int)(WHEELSIZE * unit /2);
		int wheelSize = (int)(WHEELSIZE * unit);
		Ellipse2D.Double wheel = new Ellipse2D.Double(wheelXUpLeft,  wheelYUpLeft,  wheelSize, wheelSize);
		
		//pedals
		//gives decimal percent of one revolution
		double percentOfRev = (status % (WHEELSIZE * unit * Math.PI)) / (WHEELSIZE * unit * Math.PI);
		double angle = percentOfRev * 2 * Math.PI;
		int pedalXTop = rodXBot + (int)((PEDALSIZE / 2 * unit) * Math.cos(angle));
		int pedalYTop = rodYBot + (int)((PEDALSIZE / 2 * unit) * Math.sin(angle));
		int pedalXBot = 2 * rodXBot - pedalXTop;
		int pedalYBot = 2 * rodYBot - pedalYTop;
		Line2D.Double pedal = new Line2D.Double(pedalXTop, pedalYTop, pedalXBot, pedalYBot);
		
		//stepA (pedal things)
		int stepAXUpLeft = pedalXTop - (int)(STEPSIZE / 2 * unit);
		int stepAYUpLeft = pedalYTop - (int)(STEPHEIGHT / 2 * unit);
		int stepSize = (int)(STEPSIZE * unit);
		int stepHeight = (int)(STEPHEIGHT * unit);
		Rectangle2D.Double stepA = new Rectangle2D.Double(stepAXUpLeft, stepAYUpLeft, stepSize, stepHeight);
		
		//stepB (pedal things)
		int stepBXUpLeft = pedalXBot - (int)(STEPSIZE / 2 * unit);
		int stepBYUpLeft = pedalYBot - (int)(STEPHEIGHT / 2 * unit);
		stepSize = (int)(STEPSIZE * unit);
		stepHeight = (int)(STEPHEIGHT * unit);
		Rectangle2D.Double stepB = new Rectangle2D.Double(stepBXUpLeft, stepBYUpLeft, stepSize, stepHeight);
		
		g2D.draw(seat);
		g2D.draw(rod);
		g2D.draw(hole);
		g2D.draw(wheel);
		g2D.draw(pedal);
		g2D.fill(stepA);
		g2D.fill(stepB);
		
	}
}
