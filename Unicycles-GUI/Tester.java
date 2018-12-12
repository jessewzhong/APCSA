import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
/**
 * Class gives methods that test the graphics of each part, incorporating Timers and 
 * Sliders to control movement, see JRK's file online
 * 
 * @author Jesse Zhong, jwz2111
 *
 */
public class Tester {
	/**
	 * Method tests part 1, the constant movement of a single Unicycle
	 */
	public void testA() {
		JFrame myFrame = new JFrame();
		final MovingObject myObj = new Unicycle(0, 0, SIZE);
		final MyIcon myIcon = new MyIcon(myObj, ICON_W, ICON_H);
		final JLabel myLabel = new JLabel(myIcon);
		myFrame.add(myLabel);
		myFrame.setLayout(new FlowLayout());
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.pack();
		myFrame.setVisible(true);
		
		final int DELAY = 20; //ms between timer ticks
		Timer myTimer = new Timer(DELAY, new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				myObj.translate(1, 0);
				myLabel.repaint();
			}
		});
		myTimer.start();
	}
	/**
	 * Method tests part 2, the constant movement of a group of Unicycles under user
	 * manipulation through a Slider
	 */
	public void testB() {
		JFrame myFrame = new JFrame();
		myFrame.setLayout(new BorderLayout());
		final MovingObject myObj1 = new Unicycle(200, 0, (int) (0.5*SIZE));
		final MovingObject myObj2 = new Unicycle(100, 90, SIZE);
		final MovingObject myObj3 = new Unicycle(0, 0, (int) (1.5*SIZE));
		final Figures myObj = new Figures(); //creates a "box" holding various objects
		myObj.add(myObj1);
		myObj.add(myObj2);
		myObj.add(myObj3);
		final MyIcon myIcon = new MyIcon(myObj, ICON_W, ICON_H); //creates icon with MovingObject and methods
		final JLabel myLabel = new JLabel(myIcon);
		myFrame.add(myLabel, BorderLayout.CENTER);
		final JPanel myPanel = new JPanel(new BorderLayout()); //panel to hold slider and JLabel text
		final JSlider mySlider = new JSlider(-100, 100);
		mySlider.setMajorTickSpacing(10);
		mySlider.setPaintTicks(true);
		mySlider.setPaintLabels(true);
		mySlider.addChangeListener(new ChangeListener() {
			Timer myTimer;
			public void stateChanged(ChangeEvent event) {
				if (myTimer != null) //stops current timer
					myTimer.stop();
				if (mySlider.getValue() == 0) { //doesn't do anything if slider at 0
					//nothing
				}
				else { //sets timer to corresponding delay based on pixels per second
					int DELAY = 1000 / Math.abs(mySlider.getValue());
					myTimer = new Timer(DELAY, new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							if (mySlider.getValue() > 0)
								myObj.translate(1, 0);
							else if (mySlider.getValue() < 0)
								myObj.translate(-1, 0);
							myLabel.repaint();
						}
					});
					myTimer.start();
				}
			}
		});
		myPanel.add(mySlider, BorderLayout.CENTER);
		myPanel.add(new JLabel("pixels per second", SwingConstants.CENTER), BorderLayout.SOUTH);
		myFrame.add(myPanel, BorderLayout.SOUTH); //adds panel to frame
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.pack();
		myFrame.setVisible(true);
	}
	/**
	 * Method tests part 3, which is the same as part 2 but also adding on pedals to the Unicycles
	 */
	public void testC() {
		JFrame myFrame = new JFrame();
		myFrame.setLayout(new BorderLayout());
		final MovingObject myObj1 = new Unicycle(200, 0, (int) (0.5*SIZE));
		final MovingObject myObj2 = new Unicycle(100, 90, SIZE);
		final MovingObject myObj3 = new Unicycle(0, 0, (int) (1.5*SIZE));
		final Figures myObj = new Figures(); //creates a "box" holding various objects
		myObj.add(myObj1);
		myObj.add(myObj2);
		myObj.add(myObj3);
		final MyIcon myIcon = new MyIcon(myObj, ICON_W, ICON_H); //creates icon with MovingObject and methods
		final JLabel myLabel = new JLabel(myIcon);
		myFrame.add(myLabel, BorderLayout.CENTER);
		final JPanel myPanel = new JPanel(new BorderLayout()); //panel to hold slider and JLabel text
		final JSlider mySlider = new JSlider(-100, 100);
		mySlider.setMajorTickSpacing(10);
		mySlider.setPaintTicks(true);
		mySlider.setPaintLabels(true);
		mySlider.addChangeListener(new ChangeListener() {
			Timer myTimer;
			public void stateChanged(ChangeEvent event) {
				if (myTimer != null) //stops current timer
					myTimer.stop();
				if (mySlider.getValue() == 0) { //doesn't do anything if slider at 0
					//nothing
				}
				else { //sets timer to corresponding delay based on pixels per second
					int DELAY = 1000 / Math.abs(mySlider.getValue());
					myTimer = new Timer(DELAY, new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							if (mySlider.getValue() > 0)
								myObj.translate(1, 0);
							else if (mySlider.getValue() < 0)
								myObj.translate(-1, 0);
							myLabel.repaint();
						}
					});
					myTimer.start();
				}
			}
		});
		myPanel.add(mySlider, BorderLayout.CENTER);
		myPanel.add(new JLabel("pixels per second", SwingConstants.CENTER), BorderLayout.SOUTH);
		myFrame.add(myPanel, BorderLayout.SOUTH); //adds panel to frame
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.pack();
		myFrame.setVisible(true);
	}

	private static final int SIZE = 40;
	/**
	 * width of Icon, used to wrap Unicycles in frame
	 */
	public static final int ICON_W = 600;
	/**
	 * height of Icon, used to wrap Unicycles in frame
	 */
	public static final int ICON_H = 250;
	
}
