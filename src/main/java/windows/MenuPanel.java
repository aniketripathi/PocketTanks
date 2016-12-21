package windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class MenuPanel extends JPanel implements Runnable{
	
	private static final long serialVersionUID = 1L;
	
	private Thread paintThread;
	private Timer paintTimer;
	private Graphics2D graphics;
	private final int BLOCKS = 20;
	private Region wholeRegion;
	private Region playRegion;
	private Region optionsRegion;
	private Region exitRegion;
	private Region titleRegion;
	private Region tankRegion;
	private Image titleImage;
	private Image tankImage;
	private char direction; // right or left
	
	public MenuPanel(){
		super();
		createRegions();
		titleImage = null;
		tankImage = null;
		direction = 'r';
		paintThread = new Thread(this, "paintThread");
		paintThread.start();
		
	}


	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		this.graphics = (Graphics2D)graphics;
		
		render();
	}
	
	private void createRegions(){
		// whole menu panel is the region
			wholeRegion = new Region(Frames.WIDTH/2, Frames.HEIGHT/2, Frames.WIDTH, Frames.HEIGHT);
		// divide the region into 20 blocks
			int unit_x = wholeRegion.getUnitX(BLOCKS), unit_y = wholeRegion.getUnitY(BLOCKS);
			//System.out.println("unitx = "+unit_x +"unit y= " + unit_y);
			
			/** creating regions for drawing. Match the size of the objects to draw with the region where it belongs. 
			 * Note that images and strings are drawn at center. Images are drawn from upper left corner where strings from lower left corner.
			 * To check the region call region.draw() method to check whether the region matches the particular object or not.
			 * **/
			playRegion = new Region((unit_x * 9 + unit_x * 10)/2, (unit_y * 6 + unit_y *7)/2, 2 * unit_x, unit_y);
			
			optionsRegion = new Region(playRegion);
			optionsRegion.y += 2 * unit_y;			// little below play region, one unit for space.
			optionsRegion.width *= 2;				// options contains more letters than play hence width will be more.
			
			exitRegion = new Region(playRegion);
			exitRegion.y = optionsRegion.y + 2 * unit_y;	//little below option region, one unit for space
			
			titleRegion = new Region(unit_x * 10, unit_y * 3 ,unit_x * 10, unit_y * 3); // width = approximately 10 unit while height = approximately 3 units
			
			tankRegion = new Region(unit_x * 10, unit_y * 17, unit_x * 3, unit_y * 2);
	}
	
	
	public void render(){
		
		graphics.setBackground(Color.BLUE);
		// remove the comment to view the grid
		wholeRegion.drawGrid(graphics, BLOCKS, BLOCKS);
		
		// loading title image
		
		try {
			titleImage = ImageIO.read(new File("src/main/resources/others/title.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error loading title image.");
		}
		
		try {
			tankImage = ImageIO.read(new File("src/main/resources/tank2/tank" + (getTankImageNumber())+".png"));
		}
		catch(IOException e){
			e.printStackTrace();
			System.err.println("Error loading tank image.");
		}
		
		graphics.setFont(new Font("Cursive", Font.BOLD|Font.ITALIC, 50));
		graphics.setColor(java.awt.Color.RED);
		
		
		graphics.drawString("PLAY", playRegion.x - playRegion.width/2, playRegion.y + playRegion.height/2); 					// upper left corner of region
		graphics.drawString("OPTIONS", optionsRegion.x - optionsRegion.width/2, optionsRegion.y + optionsRegion.height/2);		// upper left corner of region
		graphics.drawString("EXIT", exitRegion.x - exitRegion.width/2, exitRegion.y + exitRegion.height/2);						//upper left corner of region
		graphics.drawImage(titleImage, titleRegion.x - titleRegion.width/2, titleRegion.y - titleRegion.height/2, null);		// lower left corner of region
		
		//graphics.drawImage(tankImage, tankRegion.x - tankRegion.width/2, tankRegion.y - tankRegion.height/2, null);
		//if(direction == 'r') graphics.drawLine(tankRegion.x, tankRegion.y, tankRegion.x + 25, tankRegion.y - 25);
		
		
		/*Remove the comment to view the regions*/
		/* playRegion.draw(graphics);
		optionsRegion.draw(graphics);
		exitRegion.draw(graphics);
		titleRegion.draw(graphics);
		tankRegion.draw(graphics);*/
	
		
		
	}
	
	public void updateTankRegion(){
		final int k = 3;
		if(direction == 'r'){
			if(tankRegion.x + tankRegion.width/2 + 1 >= wholeRegion.x + wholeRegion.width/2){
				tankRegion.x -= k;
				direction = 'l';
			}
			else tankRegion.x += k;
		}
		if(direction == 'l'){
			if(tankRegion.x - tankRegion.width/2 + 1 <= wholeRegion.x - wholeRegion.width/2){
				tankRegion.x +=k;
				direction = 'r';
			}
			else tankRegion.x -=k;
		}
		
	}
	
	private int getTankImageNumber(){
		return (tankRegion.x/10)%5 + 1;
		
	}
	
	
	public void animate(){
		this.repaint();
		this.updateTankRegion();
	}

	@Override
	public void run() {
		paintTimer = new Timer(12, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				animate();
				
			}
			
		});
				paintTimer.start();
	}
	
	
}
