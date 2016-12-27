package windows;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import environment.Region;

/**
 * @author Aniket 
 *
 */
public class MenuPanel extends JPanel implements Runnable, MouseListener, ComponentListener, MouseMotionListener{
	
	private static final long serialVersionUID = 1L;
	
	private Thread paintThread;
	private Timer paintTimer;
	private Graphics2D graphics;
	private Region wholeRegion;
	private Region playRegion;
	private Region optionsRegion;
	private Region exitRegion;
	private Region titleRegion;
	private Region tankRegion;
	private BufferedImage titleImage;
	private BufferedImage tankImage;
	private StringBuffer tankImageFileName;
	private char direction; // right or left
	private final int TANK_NUMBER_INDEX	= 39;
	private CardLayout cardLayout;
	
	
	
	
	public MenuPanel(CardLayout cardLayout){
		super();
		createRegions();
		titleImage = null;
		tankImage = null;
		direction = 'r';
		paintThread = new Thread(this, "paintThread");
		tankImageFileName = new StringBuffer();
		tankImageFileName.append("src/main/resources/menuPanel/tank1/tank1.png");
		this.cardLayout = cardLayout;
		addMouseListener(this);
		addMouseMotionListener(this);
		paintThread.start();
		this.setBackground(Color.getHSBColor(0.2f, 0.6f, 0.7f));
	}


	
	
	
	
	private void createRegions(){
		final int BLOCKS = 20;		// we don't need very high precision, to increase precision increase BLOCKS and adjust regions accordingly
		// whole menu panel is the region
			wholeRegion = new Region(Frames.WIDTH/2, Frames.HEIGHT/2, Frames.WIDTH, Frames.HEIGHT);
		// divide the region into 20 blocks, increase number of blocks for greater precision
			int unit_x = wholeRegion.getUnitX(BLOCKS), unit_y = wholeRegion.getUnitY(BLOCKS);
			//System.out.println("unit x = "+unit_x +"unit y= " + unit_y);
			
			/** creating regions for drawing. Match the size of the objects to draw with the region where it belongs. 
			 * Note that images and strings are drawn at center. Images are drawn from upper left corner where strings from lower left corner.
			 * To check the region call region.draw() method to check whether the region matches the particular object or not.
			 * **/
			playRegion = new Region((unit_x * 9 + unit_x * 10)/2, (unit_y * 6 + unit_y *7)/2, 2 * unit_x, unit_y);
			
			optionsRegion = new Region(playRegion);
			optionsRegion.y += 2 * unit_y;			// little below play region, one unit for space.
			optionsRegion.width *= 2;				// options word contains more letters than play word hence width will be more.
			
			exitRegion = new Region(playRegion);
			exitRegion.y = optionsRegion.y + 2 * unit_y;	//little below option region, one unit for space
			
			titleRegion = new Region(unit_x * 10, unit_y * 3 ,unit_x * 10, unit_y * 3); // width = approximately 10 unit while height = approximately 3 units
			
			tankRegion = new Region(unit_x * 10, unit_y * 17, unit_x * 3, unit_y * 2 + 10);
		}
	
	
	
	
	public void render(){
		
		graphics.setBackground(Color.BLUE);
		
		// remove the comment to view the grid
		//wholeRegion.drawGrid(graphics, BLOCKS, BLOCKS);
		
		// loading title image
		
		try {
			titleImage = ImageIO.read(new File("src/main/resources/others/title.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error loading title image.");
		}
		
		try {
			tankImage = ImageIO.read(new File(tankImageFileName.toString()));
		}
		catch(IOException e){
			e.printStackTrace();
			System.err.println("Error loading tank image.");
		}
		tankImageFileName.replace(TANK_NUMBER_INDEX, TANK_NUMBER_INDEX+1, new Integer(getTankImageNumber(tankRegion)).toString());
		
		
		
		graphics.setFont(new Font("Cursive", Font.BOLD|Font.ITALIC, 50));
		graphics.setColor(java.awt.Color.RED);
		
		
		graphics.drawString("PLAY", playRegion.x - playRegion.width/2, playRegion.y + playRegion.height/2); 					// upper left corner of region
		graphics.drawString("OPTIONS", optionsRegion.x - optionsRegion.width/2, optionsRegion.y + optionsRegion.height/2);		// upper left corner of region
		graphics.drawString("EXIT", exitRegion.x - exitRegion.width/2, exitRegion.y + exitRegion.height/2);						//upper left corner of region
		graphics.drawImage(titleImage, titleRegion.x - titleRegion.width/2, titleRegion.y - titleRegion.height/2, null);		// lower left corner of region
		
		graphics.drawImage(tankImage, tankRegion.x - tankRegion.width/2, tankRegion.y - tankRegion.height/2, null);
		//if(direction == 'r') graphics.drawLine(tankRegion.x, tankRegion.y, tankRegion.x + 25, tankRegion.y - 25);
		
		
		/*Remove the comment to view the regions*/
		/* playRegion.draw(graphics);
		optionsRegion.draw(graphics);
		exitRegion.draw(graphics);
		titleRegion.draw(graphics);
		tankRegion.draw(graphics);
		 */
	}
	
	
	
	public void updateTankRegion(){
	/** rate of change of tank center **/	
		final int rate = 2;
		if(direction == 'r'){
			if(tankRegion.x + tankRegion.width/2 + 1 >= wholeRegion.x + wholeRegion.width/2){
				tankRegion.x -= rate;
				direction = 'l';
			}
			else tankRegion.x += rate;
		}
		if(direction == 'l'){
			if(tankRegion.x - tankRegion.width/2 + 1 <= wholeRegion.x - wholeRegion.width/2){
				tankRegion.x +=rate;
				direction = 'r';
			}
			else tankRegion.x -=rate;
		}
		
	}
	
	private static int getTankImageNumber(Region region){
		return (region.x/10)%7 + 1;
		
	}
	
	
	
	public void animate(){
		Rectangle rec = new Rectangle(tankRegion.width, tankRegion.height);
		rec.setLocation(tankRegion.x - tankRegion.width/2, tankRegion.y - tankRegion.height/2);
	/** only draw a specific region **/	
		this.repaint(rec);
		this.updateTankRegion();
	}

	@Override
	public void run() {
		paintTimer = new Timer(10, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				animate();
			}
		});
			paintTimer.start();
	}


	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		this.graphics = (Graphics2D)graphics;
		render();
	}
	
	
	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
	if(mouseEvent.getButton() == MouseEvent.BUTTON1){	
		
			 if(playRegion.isInside(mouseEvent.getX(), mouseEvent.getY()))		cardLayout.show(((JPanel)mouseEvent.getSource()).getParent(), "gamePanel");
		
		else if(optionsRegion.isInside(mouseEvent.getX(), mouseEvent.getY()))	cardLayout.show(((JPanel)mouseEvent.getSource()).getParent(), "optionsPanel");
		
		else if(exitRegion.isInside(mouseEvent.getX(), mouseEvent.getY())){
				paintTimer.stop();
				try {
					paintThread.join();
					} 
				catch (InterruptedException e) {
						e.printStackTrace();
						}
				System.exit(0);
			}
	}
	}
	
	
	
	@Override
	public void mouseMoved(MouseEvent mouseEvent) {
		int x = mouseEvent.getX(), y = mouseEvent.getY();
		if(playRegion.isInside(x, y) || optionsRegion.isInside(x, y) || exitRegion.isInside(x, y)){
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
		else this.setCursor(Cursor.getDefaultCursor());
	}


	@Override
	public void componentHidden(ComponentEvent arg0) {
		paintTimer.stop();
		try {
			paintThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void componentShown(ComponentEvent arg0) {
		paintThread.start();
	}

	
	
/** Unnecessary methods, their definitions are empty and hence they behave as no-operation **/	
	@Override
	public void componentMoved(ComponentEvent arg0) {}
	@Override
	public void componentResized(ComponentEvent arg0) {}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseDragged(MouseEvent arg0) {}

	
	
}
