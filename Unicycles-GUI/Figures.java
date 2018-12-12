import java.awt.*;
import java.util.ArrayList;
/**
 * Class represents the "tree" part of the composite pattern, holding 
 * an ArrayList of MovingObjects, specifying methods that then rely on the 
 * same methods but for each MovingObject
 * 
 * @author Jesse Zhong, jwz2111
 *
 */
public class Figures implements MovingObject {
	private ArrayList<MovingObject> items;
	
	/**
	 * Constructor initializes the ArrayList
	 */
	public Figures() {
		items = new ArrayList<MovingObject>();
	}
	
	/**
	 * allows user to add MovingObjects to the Figures
	 * @param obj the MovingObject the user wishes to add
	 */
	public void add(MovingObject obj) {
		items.add(obj);
	}
	
	/**
	 * translates the Figures by translating each of its MovingObjects
	 */
	public void translate(int xChange, int yChange) {
		for (MovingObject m : items) {
			m.translate(xChange, yChange);
		}
	}
	
	/**
	 * draws the Figures by drawing each of its MovingObjects
	 */
	public void draw(Graphics2D g2D) {
		for (MovingObject m : items) {
			m.draw(g2D);
		}
	}
}
