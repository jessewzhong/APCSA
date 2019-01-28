import java.awt.Color;
import java.awt.Font;

/**
 * class stores the values for a Throw
 * 
 * @author Jesse Zhong, jwz2111
 *
 */
public class Throw {

	private String name;
	private Font font;
	private Color color;
	private int x;
	private int y;
	private int yDirection;
	private int xDirection;
	
	/**
	 * creates a Throw object
	 * @param name String
	 * @param font Font
	 * @param color Color
	 * @param xStart int
	 * @param yStart int
	 * @param xDirection int
	 * @param yDirection int
	 */
	public Throw (String name, Font font, Color color, int xStart, int yStart, int xDirection, int yDirection) {
		this.name = name;
		this.font = font;
		this.color = color;
		this.x = xStart;
		this.y = yStart;
		this.xDirection = xDirection;
		this.yDirection = yDirection;	
	}
	
	/**
	 * returns name
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * updates the x and y anchors
	 */
	public void update() {
		x += xDirection;
		y += yDirection;
	}
	
	/**
	 * sets the x and y anchors
	 * @param xx int
	 * @param yy int
	 */
	public void set(int xx, int yy) {
		x = xx;
		y = yy;
	}
	
	/**
	 * returns x
	 * @return int
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * returns y
	 * @return int
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * returns gont
	 * @return Font
	 */
	public Font getFont() {
		return font;
	}
	
	/**
	 * sets font
	 * @param inFont Font
	 */
	public void setFont(Font inFont) {
		font = inFont;
	}
	
	/**
	 * returns color
	 * @return Color
	 */
	public Color getColor() {
		return color;
	}
	
}
