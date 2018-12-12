import java.awt.*;
import javax.swing.*;

/**
 * Class implements the Icon interface and specifies the paintIcon method
 * using its MovingObject's draw method, see JRK's file online
 * 
 * @author JRK, used by Jesse Zhong, jwz2111
 *
 */
public class MyIcon implements Icon {
	public MyIcon(MovingObject obj, int w, int h) {
		this.obj = obj;
		this.w = w;
		this.h = h;
	}
	

	public int getIconWidth() {
		return w;
	}
	
	public int getIconHeight() {
		return h;
	}
	
	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2D = (Graphics2D) g;
		obj.draw(g2D);
	}
	
	private int w;
	private int h;
	private MovingObject obj;
}
