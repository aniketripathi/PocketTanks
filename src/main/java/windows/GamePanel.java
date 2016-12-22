package windows;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import environment.Region;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JProgressBar;
import javax.swing.SpinnerNumberModel;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Region wholeRegion;
	private Region gameRegion;
	private Region propertyRegion;
	private final int UNIT_X = 8;
	private final int UNIT_Y = 8;
	private final int BLOCKS_X = Frames.WIDTH/UNIT_X;
	private final int BLOCKS_Y = Frames.WIDTH/UNIT_Y;
	private JSlider powerSlider1 = new JSlider();
	private JSlider powerSlider2 = new JSlider();
	private JComboBox<?> weaponsListComboBox1 = new JComboBox<Object>();
	private JComboBox<?> weaponsListComboBox2 = new JComboBox<Object>();
	private JSpinner	angleSpinner1 = new JSpinner();
	private JSpinner 	angleSpinner2 = new JSpinner();
	
	public GamePanel(CardLayout cardLayout){
		super();
		createRegions();
		setLayout(null);
		
		
		add(powerSlider1);
		powerSlider1.setSize(200, 25);
		powerSlider1.setLocation(UNIT_X * 20, UNIT_Y * 75);
		
		add(powerSlider2);
		powerSlider2.setSize(200, 25);
		powerSlider2.setLocation(Frames.WIDTH/2 + UNIT_X * 20, UNIT_Y * 75);
	
		add(weaponsListComboBox1);
		weaponsListComboBox1.setSize(150, 20);
		weaponsListComboBox1.setLocation(powerSlider1.getX(), powerSlider1.getY() - 30);
		
		add(weaponsListComboBox2);
		weaponsListComboBox2.setSize(150, 20);
		weaponsListComboBox2.setLocation(powerSlider2.getX(), powerSlider2.getY() - 30);
		angleSpinner1.setModel(new SpinnerNumberModel(new Integer(60), new Integer(0), new Integer(180), new Integer(1)));
		
		add(angleSpinner1);
		angleSpinner1.setSize(75, 20);
		angleSpinner1.setLocation(weaponsListComboBox1.getX() + 200, weaponsListComboBox1.getY() );
		angleSpinner1.setValue(new Integer(60));
		
		add(angleSpinner2);
		angleSpinner2.setSize(75, 20);
		angleSpinner2.setLocation(weaponsListComboBox2.getX() + 200, weaponsListComboBox2.getY() );
		angleSpinner2.setModel(angleSpinner1.getModel());
		angleSpinner2.setValue(new Integer(120));
		
	}
	
	
	
	
	
	private void createRegions(){
		wholeRegion = new Region(Frames.WIDTH/2, Frames.HEIGHT/2, Frames.WIDTH, Frames.HEIGHT);
		
		gameRegion = wholeRegion.getRegion(100, 80);		// 80 % of original region in width
		gameRegion.y = (8 * wholeRegion.height/ 20); 
		
		propertyRegion = wholeRegion.getRegion(100, 20);	//20 % of original region in width
		propertyRegion.y = Frames.HEIGHT -  (2 * wholeRegion.height / 20); 
		
		
	}
	
	@Override
	public void paint(Graphics graphics){
		super.paint(graphics);
		gameRegion.drawGridWithUnit(graphics, UNIT_X, UNIT_Y);
		
		
		
	}
}
