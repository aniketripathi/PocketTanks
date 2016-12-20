package windows;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frames extends JFrame{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width * 90 / 100;
	public static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height * 90 / 100;
	public static final int LOCATION_X = WIDTH/25;
	public static final int LOCATION_Y = HEIGHT/25;

	
	
	
	public Frames(){
		super("Pocket Tanks");
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocation(LOCATION_X, LOCATION_Y);
		this.setBackground(Color.DARK_GRAY);
		}
}
