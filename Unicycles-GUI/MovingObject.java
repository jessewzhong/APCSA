import java.awt.*;

/**
 * This interface class is the composite interface object of the composite pattern
 * for the Unicycles, see JRK's file online
 * 
 * @author JRK, used by Jesse Zhong, jwz2111
 *
 */
public interface MovingObject {
	abstract void draw(Graphics2D g2D);
	abstract void translate(int xChange, int yChange);

}
