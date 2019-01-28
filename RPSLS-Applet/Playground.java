import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.*;

import javax.swing.Timer;

/**
 * creates an applet that plays RPSLS
 * 
 * needs an html file (real or simulated) that looks like 
 * 
 * @author Jesse Zhong, jwz2111, based on jrk
 * 
 *
 */
public class Playground extends Applet {
	
	private ArrayList<Throw> Throws;
	private ArrayList<Rectangle2D> Rectangles;
	private int htmlDelay;
	private long stop;
	private Timer appletTimer;
	private long time; 
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * helper method sets up the Throws and Rectangles array plus other data from HTML params
	 */
	private void setup() {
		time = System.nanoTime();
		ArrayList<String> htmlThrowName = new ArrayList<String>(Arrays.asList(getParameter("throw").split(" ")));
		
		//gets the String names of the fonts, replacing _ with spaces using split 
		ArrayList<String> htmlFontName = new ArrayList<String>(Arrays.asList(getParameter("fontname").split(" ")));
		for (int i = 0; i < htmlFontName.size(); i++) {
			String temp = htmlFontName.get(i);
			while (temp.indexOf('_') != -1){
				int index = temp.indexOf('_');
				temp = temp.substring(0, index) + " " + temp.substring(index + 1);
			}
			htmlFontName.set(i, temp);
		}
		ArrayList<String> htmlFontSize = new ArrayList<String>(Arrays.asList(getParameter("fontsize").split(" ")));
		ArrayList<String> htmlFontType = new ArrayList<String>(Arrays.asList(getParameter("fonttype").split(" ")));
		ArrayList<String> htmlFontColor = new ArrayList<String>(Arrays.asList(getParameter("fontcolor").split(" ")));
		ArrayList<Color> allColors = new ArrayList<Color>();
		
		//uses reflection to get Color objects from String names
		for (int i = 0; i < htmlFontColor.size(); i++) {
			try {
				allColors.add((Color) Color.class.getField(htmlFontColor.get(i)).get(null));
			}
			catch (Exception e) {
				//handle
			}
		}

		ArrayList<Font> throwFont = new ArrayList<Font>();
		
		//uses the decode method to create Font objects
		for (int i = 0; i < htmlFontName.size(); i++) {
			throwFont.add(Font.decode(htmlFontName.get(i) + " " + htmlFontType.get(i) + " " + htmlFontSize.get(i)));
		}
		
		ArrayList<Integer> throwY = new ArrayList<Integer>(Arrays.asList(convert(getParameter("ystart").split(" "))));
		ArrayList<Integer> throwX = new ArrayList<Integer>(Arrays.asList(convert(getParameter("xstart").split(" "))));
		ArrayList<Integer> directionX = new ArrayList<Integer>(Arrays.asList(convert(getParameter("xdirection").split(" "))));
		ArrayList<Integer> directionY = new ArrayList<Integer>(Arrays.asList(convert(getParameter("ydirection").split(" "))));
		
		//creates ArrayList of Throws from data
		Throws = new ArrayList<Throw>();
		for (int i = 0; i < htmlThrowName.size(); i++) {
			Throws.add(new Throw(htmlThrowName.get(i), throwFont.get(i), allColors.get(i), throwX.get(i), throwY.get(i), directionX.get(i), directionY.get(i)));
		}
		
		
		// idioms to establish Graphics environment
		Graphics2D g2D = (Graphics2D) getGraphics();
		// idioms to get information about the applet "paintbrushes"
		FontRenderContext throwContext = g2D.getFontRenderContext();
		// get the bounding rectangle around htmlThrow when painted

		//creates bounding Rectangles for the Throws, different for Black Holes vs RPSLS
		Rectangles = new ArrayList<Rectangle2D>();
		for (int i = 0; i < Throws.size(); i++) {
			Throw t = Throws.get(i);
			if (t.getName().equals("BlackHole")) {
				Rectangle2D temp = new Rectangle2D.Double(t.getX(), t.getY(), t.getFont().getSize(), t.getFont().getSize());
				Rectangles.add(temp);
			}
			else {
				Rectangle2D temp = t.getFont().getStringBounds(t.getName(), throwContext);
				temp.setRect(t.getX(), t.getY() - temp.getHeight(), temp.getWidth(), temp.getHeight());
				Rectangles.add(temp);
			}
		}
		
	}
	
	/**
	 * Idioms for applet initialization, mostly to get parameters; use
	 * convention that incoming parameters are prefixed with "html"
	 */
	public void init() {
		
		setup();
		// info for the timer
		// note that htmlDelay has to be available for the listener
		htmlDelay = Integer.parseInt(getParameter("delay"));
		stop = Long.parseLong(getParameter("stop"));
		// usual Timer idiom
		appletTimer = new Timer(htmlDelay, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Graphics2D g2D = (Graphics2D) getGraphics();
				g2D.setColor(Color.MAGENTA);
				FontRenderContext throwContext = g2D.getFontRenderContext();
				
				//Algorithm to add indices of winners and losers of Throws to respective ArrayLists
				ArrayList<Integer> toBeRemoved = new ArrayList<Integer>();
				ArrayList<Integer> winners = new ArrayList<Integer>();
				for (int i = 0; i < Rectangles.size() - 1; i++) {
					for (int j = i + 1; j < Rectangles.size(); j++) {
						if (Rectangles.get(i).intersects(Rectangles.get(j))) {
							String a = Throws.get(i).getName();
							String b = Throws.get(j).getName();
							if (a.equals("BlackHole") && b.equals("BlackHole")) {
								if (Throws.get(i).getFont().getSize() > Throws.get(j).getFont().getSize()) {
									toBeRemoved.add(j);
									winners.add(i);
								}
								else {
									toBeRemoved.add(i);
									winners.add(j);
								}
							}
							else if (a.equals("BlackHole")) {

								toBeRemoved.add(j);
								winners.add(i);
							}
							else if (b.equals("BlackHole")) {
								toBeRemoved.add(i);
								winners.add(j);
							}
							else {
								if (Rules.checkWin(a, b) == true) {
									toBeRemoved.add(j);
									winners.add(i);
								}
								else if (Rules.checkWin(b, a) == true) {
									toBeRemoved.add(i);
									winners.add(j);
								}
							}
						}
					}
				}
				
				//increases the size of winners by proportional factor to loser's size
				//prints out message near collision based on location of winner/loser
				for (int i = 0; i < winners.size(); i++) {
					int a = toBeRemoved.get(i);
					int b = winners.get(i);
					int x;
					int y;
					if (Throws.get(a).getX() <= getWidth() / 2) {
						x = (int) (Rectangles.get(a).getX() + Rectangles.get(a).getWidth());
					}
					else {
						x = (int) Rectangles.get(a).getX() - getWidth() / 4;
					}
					y = (int) (Rectangles.get(a).getY() + Rectangles.get(a).getHeight());
					g2D.setFont(Font.decode("Arial BLACK BOLD 20"));
					g2D.drawString(Throws.get(b).getName() + " beats " + Throws.get(a).getName(), x, y);
					double increase = (double) Throws.get(a).getFont().getSize() / Throws.get(b).getFont().getSize() + 1;
					Throws.get(b).setFont(Throws.get(b).getFont().deriveFont(Throws.get(b).getFont().getSize() * (float) increase));
				}
				
				//sort the removed array and remove repeats
				Collections.sort(toBeRemoved);
				if (toBeRemoved.size() >= 2) {
					for (int i = toBeRemoved.size() - 2; i >= 0; i--) {
						if (toBeRemoved.get(i) == toBeRemoved.get(i + 1)) {
							toBeRemoved.remove(i);
						}
					}
				}
				
				//removes losers from ArrayLists
				for (int i = toBeRemoved.size() - 1; i >= 0; i--) {
					Throws.remove((int) toBeRemoved.get(i));
					Rectangles.remove((int) toBeRemoved.get(i));
				}
				
				//pauses Thread if a collision occurred
				if (toBeRemoved.size() > 0) {
					time = System.nanoTime(); //resets current time since collision
					try {
						Thread.sleep(2000);
					}
					catch (Exception ee) {	
					}
				}
				
				//tests if there hasn't been a collision in specified amount of time, stops if true
				if (System.nanoTime() - time >= stop) {
					g2D.setFont(Font.decode("Arial BLACK BOLD 50"));
					g2D.drawString("DONE", getWidth() / 2, getHeight() / 2);
					stop();
					return;
				}

				
				// move to the left one pixel of all Throws
				for (int i = 0; i < Throws.size(); i++){
					Throw temp = Throws.get(i);
					temp.update();
					int xTemp = temp.getX();
					int yTemp = temp.getY();
					//setting the "center/anchor point" of a black hole to be the middle of the oval
					if (temp.getName().equals("BlackHole")) {
						temp.set(Math.floorMod(xTemp + temp.getFont().getSize() / 2, getWidth()) - temp.getFont().getSize() / 2, Math.floorMod(yTemp + temp.getFont().getSize() / 2, getHeight()) - temp.getFont().getSize() / 2);
					}
					else {
						temp.set(Math.floorMod(xTemp, getWidth()), Math.floorMod(yTemp, getHeight()));
					}
				}
				
				//Updates Rectangles
				for (int i = 0; i < Rectangles.size(); i++) {
					Throw t = Throws.get(i);
					if (t.getName().equals("BlackHole")) {
						Rectangle2D temp = new Rectangle2D.Double(t.getX(), t.getY(), t.getFont().getSize(), t.getFont().getSize());
						Rectangles.set(i, temp);
					}
					else {
						Rectangle2D temp = t.getFont().getStringBounds(t.getName(), throwContext);
						temp.setRect(t.getX(), t.getY() - temp.getHeight(), temp.getWidth(), temp.getHeight());
						Rectangles.set(i, temp);
					}
				}
				
				
				repaint();
			}
		});
	}

	/**
	 * helper method converts String array to Integer array
	 * @param a String array
	 * @return Integer array
	 */
	private Integer[] convert(String[] a) {
		Integer[] temp = new Integer[a.length];
		for (int i = 0; i < a.length; i++) {
			temp[i] = Integer.parseInt(a[i]);
		}
		return temp;
	}
	
	/**
	 * starts the applet
	 */
	public void start() {
		appletTimer.start();
	}
	
	
	/**
	 * defines the paint method
	 */
	public void paint(Graphics g) {
		// reset, if necessary, the paintbrush
		// do the drawing
		// note that throwX is the only thing that changes between
		// paintings
		for (int i = 0; i < Throws.size(); i++) {
			Throw temp = Throws.get(i);

			//paints a circle if Throw is a black hole
			if (temp.getName().equals("BlackHole")) {
				g.setColor(Color.BLACK);
				g.fillOval(temp.getX(), temp.getY(), temp.getFont().getSize(), temp.getFont().getSize());
			}
			else {
				g.setColor(temp.getColor());
				g.setFont(temp.getFont());
				g.drawString(temp.getName(), temp.getX(), temp.getY());
			}
		}

	}
	
	/**
	 * updates using a double buffer
	 */
    public void update(Graphics g) {
    Graphics offgc;
    Image offscreen = null;
    Dimension d = getSize();

    // create the offscreen buffer and associated Graphics
    offscreen = createImage(d.width, d.height);
    offgc = offscreen.getGraphics();
    paint(offgc);
    // transfer offscreen to window
    g.drawImage(offscreen, 0, 0, this);
    }

    /**
     * stops applet
     * 
     */
	public void stop() {
		appletTimer.stop();
	}

	/**
	 * destroy
	 */
	public void destroy() {
	}

}
